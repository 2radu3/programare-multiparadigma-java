import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static List<Specialitate> specialitati = new ArrayList<>();

    public static void afisareManevre() throws Exception {
        FileReader fr = new FileReader("medicale.json");
        JSONArray arr = new JSONArray(new JSONTokener(fr));

        for(int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Specialitate s = new Specialitate();
            s.nume = obj.getString("specialitate");
            s.manevre = new ArrayList<>();

            JSONArray manevre = obj.getJSONArray("manevre");
            for(int j = 0; j < manevre.length(); j++) {
                JSONObject m = manevre.getJSONObject(j);
                Manevra manevra = new Manevra();
                manevra.codManevra = m.getInt("cod");
                manevra.durata = m.getInt("durata");
                manevra.tarif = m.getDouble("tarif");

                s.manevre.add(manevra);
            }
            specialitati.add(s);
        }
        fr.close();

        System.out.println("===== CERINTA 1 =====");
        System.out.println(String.format("%-20s %-15s %s", "Specialitate", "Cod manevra", "Durata"));
        for(Specialitate s : specialitati) {
            s.manevre.sort((a,b)->b.durata - a.durata);
            for(Manevra m : s.manevre) {
                System.out.println(String.format("%-20s %-15d %d", s.nume, m.codManevra, m.durata));
            }
        }
    }

    public static void afisareSpecialitati() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:consultatii.db");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Consultatii");
        Map<String, Double> venituri = new HashMap<>();
        while(rs.next()) {
            String specialitate = rs.getString("Specialitate");
            int codManevra = rs.getInt("CodManevra");
            int numar = rs.getInt("Numar");
            //System.out.println("DB: " + specialitate + " " + codManevra + " " + numar);

            double tarif = 0;
            for(Specialitate s : specialitati) {
                for(Manevra m : s.manevre) {
                    if(m.codManevra == codManevra) {
                        tarif = m.tarif;
                        break;
                    }
                }
            }

            double venit = tarif * numar;
            venituri.put(specialitate, venituri.getOrDefault(specialitate, 0.0) + venit);
        }
        rs.close();
        statement.close();
        connection.close();

        List<Map.Entry<String, Double>> lista = new ArrayList<>(venituri.entrySet());
        lista.sort((a,b)->Double.compare(b.getValue(), a.getValue()));

        System.out.println("===== CERINTA 2 =====");
        System.out.println(String.format("%-20s %s", "Specialitate", "VenitGenerat"));
        for(Map.Entry<String, Double> entry : lista) {
            System.out.println(String.format("%-20s %.1f",entry.getKey(), entry.getValue()));
        }
    }


    public static void main(String[] args) throws Exception {
        afisareManevre();
        afisareSpecialitati();
    }
}
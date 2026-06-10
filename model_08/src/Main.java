import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Produs> listaProduse = new ArrayList<>();
    public static void valoareTotala() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:examen.db");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT (cantitate * pret_unitar) as Valoare FROM MateriiPrime");

        double valoare = 0.0;
        while(rs.next()) {
            valoare += rs.getDouble("Valoare");
        }
        System.out.println(valoare);

        connection.close();
        statement.close();
        rs.close();
    }

    public static void afisareProduse() throws Exception {
        FileReader fr = new FileReader("produse.json");
        JSONArray arr = new JSONArray(new JSONTokener(fr));

        for(int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Produs p = new Produs();
            p.cod = obj.getInt("Cod produs");
            p.denumire = obj.getString("Denumire produs");
            p.cantitate = obj.getInt("Cantitate");
            p.UnitateMasura = obj.getString("Unitate masura");
            p.consumuri = new ArrayList<>();

            JSONArray consumuri = obj.getJSONArray("Consumuri");
            for(int j = 0; j < consumuri.length(); j++) {
                JSONObject c = consumuri.getJSONObject(j);
                Consum consum = new Consum();
                consum.codMateriePrima = c.getInt("Cod materie prima");
                consum.Cantitate = c.getDouble("Cantitate");

                p.consumuri.add(consum);
            }
            listaProduse.add(p);
        }
        fr.close();

        listaProduse.sort((a,b)->b.consumuri.size() - a.consumuri.size());
        System.out.println("===== CERINTA 2 =====");
        System.out.println(String.format("%-10s %-30s %s", "Cod", "Denumire", "Nr materii prime"));
        for(Produs p : listaProduse) {
            System.out.println(String.format("%-10d %-30s %d", p.cod, p.denumire, p.consumuri.size()));
        }
    }

    public static void main(String[] args) throws Exception {
        valoareTotala();
        afisareProduse();
    }
}
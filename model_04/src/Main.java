import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static List<Actiune> actiuni = new ArrayList<>();
    public static List<Tranzactie> tranzactii = new ArrayList<>();

    public static void citireActiuni() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:S204_Actiuni.db");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Actiuni ORDER BY Simbol ASC");

        while(rs.next()) {
            Actiune a = new Actiune();
            a.simbol = rs.getString("Simbol");
            a.denumire = rs.getString("Denumire");
            a.ziua1 = rs.getDouble("Ziua1");
            a.ziua2 = rs.getDouble("Ziua2");
            a.ziua3 = rs.getDouble("Ziua3");
            a.ziua4 = rs.getDouble("Ziua4");
            a.ziua5 = rs.getDouble("Ziua5");

            actiuni.add(a);
        }

        rs.close();
        statement.close();
        connection.close();

        System.out.println("===== CERINTA 1 =====");
        for(Actiune a : actiuni) {
            System.out.println(String.format("%4s, %-35s, %.2f RON", a.simbol, a.denumire, a.ziua5));
        }
    }

    public static void citireTranzactii() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("S204_Tranzactii.csv"));
        String linie;
        while((linie = br.readLine()) != null) {
            linie = linie.trim();

            String[] parti = linie.split(",");
            Tranzactie t = new Tranzactie();
            t.Date = parti[0].trim();
            t.Direction = parti[1].trim();
            t.Symbol = parti[2].trim();
            t.Quantity = Integer.parseInt(parti[3].trim());
            t.Price = Double.parseDouble(parti[4].trim());

            tranzactii.add(t);
        }
        br.close();

        double totalCumparare = 0;
        double totalVanzare = 0;
        for(Tranzactie t : tranzactii) {
            double valoare = t.Quantity * t.Price;
            if(t.Direction.equals("Buy")) {
                totalCumparare += valoare;
            } else if(t.Direction.equals("Sell")) {
                totalVanzare += valoare;
            }
        }
        System.out.println("===== CERINTA 2 =====");
        System.out.println(String.format("Total cumparare: %.2f", totalCumparare));
        System.out.println(String.format("Total vanzare: %.2f", totalVanzare));
    }

    public static void generareJson() throws Exception {
        Map<String, Integer> cantitati = new HashMap<>();

        for(Tranzactie t : tranzactii) {
            int cantitate = cantitati.getOrDefault(t.Symbol, 0);
            if(t.Direction.equals("Buy")) {
                cantitati.put(t.Symbol, t.Quantity + cantitate);
            } else if(t.Direction.equals("Sell")) {
                cantitati.put(t.Symbol, cantitate - t.Quantity);
            }
        }

        JSONArray portofoliu = new JSONArray();

        for(Map.Entry<String, Integer> entry : cantitati.entrySet()) {
            String simbol = entry.getKey();
            int cantitate = entry.getValue();

            Actiune actiune = null;
            for(Actiune a : actiuni) {
                if(a.simbol.equals(simbol)) {
                    actiune = a;
                }
            }

            double valoare = actiune.ziua5 * cantitate;

            JSONObject obj = new JSONObject();
            obj.put("Simbol", simbol);
            obj.put("Denumire", actiune.denumire);
            obj.put("Cantitate", cantitate);
            obj.put("Valoare", valoare);

            portofoliu.put(obj);
        }

        List<JSONObject> lista = new ArrayList<>();
        for(int i = 0; i < portofoliu.length(); i++) {
            lista.add(portofoliu.getJSONObject(i));
        }
        lista.sort((a,b) -> b.getInt("Valoare") - a.getInt("Valoare"));

        JSONArray rezultat = new JSONArray(lista);

        FileWriter fw = new FileWriter("S204_Portofoliu.json");
        fw.write(rezultat.toString(2));
        fw.close();
    }

    public static void main(String[] args) throws Exception {
        citireActiuni();
        citireTranzactii();
        generareJson();
    }
}

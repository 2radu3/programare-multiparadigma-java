import java.io.BufferedReader;
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

    public static List<Titlu> titluri = new ArrayList<>();
    public static void citireTitluri() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:Titluri.db");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Titluri");

        Map<String, String> denumiri = new HashMap<>();
        while(rs.next()) {
            denumiri.put(rs.getString("Simbol"), rs.getString("Denumire"));
        }
        rs.close();
        statement.close();
        connection.close();

        BufferedReader br = new BufferedReader(new FileReader("PretVolum.txt"));
        br.readLine();
        String linie;
        while((linie = br.readLine()) != null) {
            linie = linie.trim();
            String[] parti = linie.split(",");

            Titlu t = new Titlu();
            t.simbol = parti[0].trim();
            t.denumire = denumiri.getOrDefault(t.simbol, "N/A");
            t.deschidere = Double.parseDouble(parti[1].trim());
            t.max = Double.parseDouble(parti[2].trim());
            t.min = Double.parseDouble(parti[3].trim());
            t.inchidere = Double.parseDouble(parti[4].trim());
            t.volum = Long.parseLong(parti[5].trim());

            titluri.add(t);
        }
        br.close();

        Titlu valoareMIN = null;
        Titlu valoareMAX = null;
        for(Titlu t : titluri) {
            double valoare = t.inchidere * t.volum;
            if(valoareMIN == null || valoare < valoareMIN.inchidere * valoareMIN.volum) {
                valoareMIN = t;
            }
            if(valoareMAX == null || valoare > valoareMAX.inchidere * valoareMAX.volum) {
                valoareMAX = t;
            }
        }

        System.out.println("===== CERINTA 1 =====");
        System.out.println("Simbol    Valoare");
        System.out.println(valoareMIN.simbol + "      " + valoareMIN.inchidere * valoareMIN.volum);
        System.out.println(valoareMAX.simbol + "      " + valoareMAX.inchidere * valoareMAX.volum);
    }

    public static void sortare() {
        titluri.sort((a,b)->Long.compare(b.volum, a.volum));
        System.out.println("===== CERINTA 2 =====");
        System.out.println(String.format("%-10s %-40s %s", "Simbol", "Denumire", "Volum"));
        for(Titlu t : titluri) {
            System.out.println(String.format("%-10s %-40s %s", t.simbol, t.denumire, t.volum));
        }
    }

    public static void diferenta() {
        List<Titlu> sortate = new ArrayList<>();
        for(Titlu t : titluri) {
            if((t.max - t.min) / t.min * 100 > 1) {
                sortate.add(t);
            }
        }

        sortate.sort((a,b)->Double.compare((b.max - b.min), (a.max - a.min)));

        System.out.println("===== CERINTA 3 =====");
        for(Titlu t : sortate) {
            System.out.println(String.format("%-10s %-40s %.2f", t.simbol, t.denumire, t.max - t.min));
        }
    }

    public static void main(String[] args) throws Exception {
        citireTitluri();
        sortare();
        diferenta();
    }
}
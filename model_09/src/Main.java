import java.io.BufferedReader;
import java.io.BufferedWriter;
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
    public static List<Carte> carti = new ArrayList<>();
    public static void cerinta1() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("carti.txt"));
        String linie;

        while((linie = br.readLine()) != null) {
            String[] parti = linie.split("\t");
            Carte c = new Carte();
            c.cota = parti[0];
            c.titlu = parti[1];
            c.autor = parti[2];
            c.an = Integer.parseInt(parti[3]);

            carti.add(c);
        }
        br.close();

        carti.sort((a,b)->a.titlu.compareTo(b.titlu));
        for(Carte c : carti) {
            if(c.an < 1940) {
                System.out.println(String.format("%-20s %-30s %-30s %-20d", c.cota, c.titlu, c.autor, c.an));
            }
        }
    }

    public static void cerinta2() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:biblioteca.db");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Imprumuturi");

        Map<String, Integer> cititori = new HashMap<>();
        while (rs.next()) {
            String nume = rs.getString("nume student");
            int zile = rs.getInt("zile imprumut");
            if(zile > 0) {
                cititori.put(nume, zile);
            }
        }
        for(Map.Entry<String, Integer> entry : cititori.entrySet()) {
            System.out.println(entry.getKey());
        }

        connection.close();
        statement.close();
        rs.close();

    }

    public static void cerinta3() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:biblioteca.db");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Imprumuturi");

        Map<String, Integer> cititori = new HashMap<>();
        while(rs.next()) {
            String nume = rs.getString("nume student");
            int zile = rs.getInt("zile imprumut");
            cititori.put(nume, cititori.getOrDefault(nume, 0) + zile);
        }
        connection.close();
        statement.close();
        rs.close();

        List<Map.Entry<String, Integer>> lista = new ArrayList<>(cititori.entrySet());
        lista.sort((a,b)->Integer.compare(b.getValue(), a.getValue()));

        BufferedWriter bw = new BufferedWriter(new FileWriter("cititori.txt"));
        bw.write("Nume Student" + "         " + "- Total zile imprumut\n");
        for(Map.Entry<String, Integer> entry : lista) {
            bw.write(entry.getKey() + "                 - " + entry.getValue() + "\n");
        }
        bw.close();
    }



    public static void main(String[] args) throws Exception {
        cerinta1();
        cerinta2();
        cerinta3();
    }
}
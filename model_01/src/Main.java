import java.io.*;
import java.sql.*;
import java.util.*;

public class Main {

    public static List<Factura> facturi2 = new ArrayList<>();
    public static void citesteDinFisier() throws Exception{
        String caleFisier = "S27_intretinere_facturi.txt";
        Factura facturaMaxima = null;
        BufferedReader br = new BufferedReader(new FileReader(caleFisier));
            String linie;
            while((linie = br.readLine()) != null){
                linie = linie.trim();
                if(linie.isEmpty()) continue;

                String[] parti = linie.split(",");

                if(parti.length == 3) {
                    String denumire = parti[0].trim();
                    String repartizare = parti[1].trim();
                    double valoare = Double.parseDouble(parti[2].trim());

                    Factura facturaCurenta = new Factura(denumire, repartizare, valoare);
                    facturi2.add(facturaCurenta);
                    if(facturaMaxima == null || facturaCurenta.getValoare() > facturaMaxima.getValoare()){
                        facturaMaxima = facturaCurenta;
                    }
                }
            }
            /*
            for(Factura f : facturi){
                System.out.println(f.toString());
            }
            */

            if(facturaMaxima != null){
                System.out.println("Factura cu valoarea maxima este: " + facturaMaxima.getDenumire() + " (" + facturaMaxima.getValoare() + ")");
            } else {
                System.out.println("Fisierul este gol sau nu contine data valide");
            }

    }

    public static void creareTabela() throws Exception{
        String url = "jdbc:sqlite:S27_intretinere.db";
        String sql = "create table if not exists Apartamente(NumarApartament integer primary key, Suprafata integer not null, NumarPersoane integer not null)";
        Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
    }

    public static void afiseazaApartamente() throws Exception{
        String url = "jdbc:sqlite:S27_intretinere.db";

        String sql = "select NumarApartament, Suprafata, NumarPersoane from Apartamente where NumarPersoane >= 2 ORDER BY NumarApartament DESC";
        List<Apartament> apartamente = new ArrayList<>();

        Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                int nrApt = rs.getInt("NumarApartament");
                int suprafata = rs.getInt("Suprafata");
                int nrPers = rs.getInt("NumarPersoane");

                Apartament apt = new Apartament(nrApt, suprafata, nrPers);
                apartamente.add(apt);
            }
            if(apartamente.isEmpty()){
                System.out.println("Nu s-au gasit apartamente");
            } else {
                for (Apartament apt : apartamente) {
                    System.out.println(apt);
                }
            }
    }

    public static void populareTabela() throws Exception{
        String url = "jdbc:sqlite:S27_intretinere.db";
        String sql = "INSERT INTO Apartamente VALUES (?, ?, ?)";

        Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, 1);
            statement.setInt(2, 70);
            statement.setInt(3, 4);
            statement.executeUpdate();

            statement.setInt(1, 2);
            statement.setInt(2, 80);
            statement.setInt(3, 6);
            statement.executeUpdate();

    }

    public static void sumarFacturi(List<Factura> facturi) throws IOException {
        String caleFisier = "date/sumar.txt";

        Map<String, Double> mapValoareTotala = new HashMap<>();
        Map<String, Integer> mapNumarFacturi = new HashMap<>();

        for(Factura f : facturi) {
            String tip = f.getRepartizare();

            double valoareCurenta = mapValoareTotala.getOrDefault(tip, 0.0);
            mapValoareTotala.put(tip, valoareCurenta + f.getValoare());

            int numarCurent = mapNumarFacturi.getOrDefault(tip, 0);
            mapNumarFacturi.put(tip, numarCurent + 1);
        }

        List<String> tipuriRepartizare = new ArrayList<>(mapValoareTotala.keySet());
        Collections.sort(tipuriRepartizare, (tip1, tip2) -> {
            double v1 = mapValoareTotala.get(tip1);
            double v2 = mapValoareTotala.get(tip2);
            return Double.compare(v2, v1);
        });

        BufferedWriter bw = new BufferedWriter(new FileWriter(caleFisier));

        bw.write("Tip Repartizare, Total Facturi, Numar Facturi\n");

        for(String tip : tipuriRepartizare){
            double totalFacturi = mapValoareTotala.get(tip);
            int numarFacturi = mapNumarFacturi.get(tip);

            bw.write(tip + "," + totalFacturi + "," + numarFacturi + "\n");
        }

        bw.close();
        System.out.println("Fisierul a fost generat cu succes");

    }

    public static void main(String[] args) throws IOException, Exception {
        //List<Factura> facturi = new ArrayList<>();
        citesteDinFisier();
        creareTabela();
        //populareTabela();
        afiseazaApartamente();
        sumarFacturi(facturi2);
    }
}
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
    public static List<Agent> agenti = new ArrayList<>();
    public static List<Imobil> imobile = new ArrayList<>();
    public static void cerinta1() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("agenti.txt"));
        String linie;
        while((linie = br.readLine()) != null) {
            String[] parti = linie.split(",");

            Agent a = new Agent();
            a.codAgent = Integer.parseInt(parti[0].trim());
            a.numeAgent = parti[1].trim();
            a.nrTel = parti[2].trim();

            agenti.add(a);
        }
        br.close();

        System.out.println("========= CERINTA 1 ========");
        for(Agent a : agenti) {
            System.out.println(a.codAgent + ", " + a.numeAgent + ", " + a.nrTel);
        }
    }

    public static void cerinta2() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:agentia.db");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM IMOBILE");
        while(rs.next()) {
            Imobil i = new Imobil();
            i.codImobil = rs.getInt("cod_imobil");
            i.codAg = rs.getInt("cod_agent");
            i.tipImobil = rs.getString("tip_imobil");
            i.pret = rs.getDouble("pret");

            imobile.add(i);
        }
        connection.close();
        statement.close();
        rs.close();

        Map<Integer, Integer> imobilePerAgent = new HashMap<>();
        for (Imobil i : imobile) {
            imobilePerAgent.put(i.codAg, imobilePerAgent.getOrDefault(i.codAg, 0) + 1);
        }

        System.out.println("======== CERINTA 2 ========");
        for(Agent a : agenti) {
            System.out.println(a.numeAgent + " " + imobilePerAgent.get(a.codAgent));
        }
    }

    public static void cerinta3() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter("jurnal.txt"));
        bw.write("======== CERINTA 3 ========\n");
        for(Agent a : agenti) {
            bw.write("\n" + a.numeAgent);
            List<Double> pretAp = new ArrayList<>();
            List<Double> pretCasa = new ArrayList<>();
            List<Double> pretTeren = new ArrayList<>();
            for(Imobil i : imobile) {
                if(i.codAg == a.codAgent) {
                    if(i.tipImobil.equals("apartament")) {
                        pretAp.add(i.pret);
                    } else if(i.tipImobil.equals("casa")) {
                        pretCasa.add(i.pret);
                    } else if(i.tipImobil.equals("teren")) {
                        pretTeren.add(i.pret);
                    }
                }
            }
            bw.write("\napartament: ");
            for(int i = 0; i < pretAp.size(); i++){
                bw.write(pretAp.get(i).toString());
                if(i < pretAp.size() - 1) bw.write(",");
            }
            bw.write("\ncasa: ");
            for(int i = 0; i < pretCasa.size(); i++){
                bw.write(pretCasa.get(i).toString());
                if(i < pretCasa.size() - 1) bw.write(",");
            }
            bw.write("\nteren: ");
            for(int i = 0; i < pretTeren.size(); i++){
                bw.write(pretTeren.get(i).toString());
                if(i < pretTeren.size() - 1) bw.write(",");
            }
        }
        bw.close();
    }

    public static void main(String[] args) throws Exception {
        cerinta1();
        cerinta2();
        cerinta3();
    }
}

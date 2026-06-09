import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.swing.*;
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

    public static List<Sectie> sectii = new ArrayList<>();
    public static List<Sectie> sectiiFiltrate = new ArrayList<>();
    public static List<Pacient> pacienti = new ArrayList<>();
    public static void afisareSectii() throws Exception {
        FileReader fr = new FileReader("sectii.json");
        JSONArray arr = new JSONArray(new JSONTokener(fr));

        for(int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Sectie s = new Sectie();

            s.codSectie = obj.getInt("cod_sectie");
            s.denumire = obj.getString("denumire");
            s.nrLocuri = obj.getInt("numar_locuri");
            if (s.nrLocuri > 5) {
                sectiiFiltrate.add(s);
            }
            sectii.add(s);
        }
        fr.close();

        for(Sectie s : sectiiFiltrate) {
            System.out.println(s.codSectie + " " + s.denumire + " " + s.nrLocuri);
        }
    }

    public static void raportInternari() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:spital.db");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT CodSectie, COUNT(*) as NrPacienti FROM Pacienti GROUP BY CodSectie");

        Map<Integer, Integer> pacientiPerSectie = new HashMap<>();
        while(rs.next()) {
            pacientiPerSectie.put(rs.getInt("CodSectie"), rs.getInt("NrPacienti"));
        }
        rs.close();
        statement.close();
        connection.close();

        System.out.println("===== CERINTA 2 =====");
        System.out.println(String.format("%-10s %-40s %-10s", "Cod Sectie", "Denumire sectie", "Numar pacienti"));
        for(Sectie s : sectii) {
            int nrPacienti = pacientiPerSectie.getOrDefault(s.codSectie, 0);
            System.out.println(String.format("%-10d %-40s %-10d", s.codSectie, s.denumire, nrPacienti));
        }
    }

    public static void scriereFisier() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:spital.db");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT CodSectie, AVG(VarstaPacient) as MedieVarsta FROM Pacienti GROUP BY CodSectie");

        Map<Integer, Double> varstaPerSectie = new HashMap<>();
        while(rs.next()) {
            varstaPerSectie.put(rs.getInt("CodSectie"), rs.getDouble("MedieVarsta"));
        }
        rs.close();
        statement.close();
        connection.close();

        sectii.sort((a,b) -> Double.compare(varstaPerSectie.getOrDefault(b.codSectie, 0.0), varstaPerSectie.getOrDefault(a.codSectie, 0.0)));

        BufferedWriter bw = new BufferedWriter(new FileWriter("situatie.txt"));
        bw.write(String.format("%-20s %-30s %-20s %-10s\n", "Cod sectie", "Denumire sectie", "Numar locuri", "Varsta medie"));
        for(Sectie s : sectii) {
            bw.write(String.format("%5d %29s %23d %21.2f \n", s.codSectie, s.denumire, s.nrLocuri, varstaPerSectie.get(s.codSectie)));
        }
        bw.close();
    }


    public static void main(String[] args) throws Exception {
        afisareSectii();
        raportInternari();
        scriereFisier();
    }
}
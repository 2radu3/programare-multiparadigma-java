import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static List<Actiune> actiuni = new ArrayList<>();
    public static List<Tranzactie> tranzactii = new ArrayList<>();
    public static void cerinta1() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("S203_Actiuni.csv"));
        String linie;
        while((linie = br.readLine()) != null) {
            String[] parti = linie.split(",");

            Actiune a = new Actiune();
            a.simbol = parti[0].trim();
            a.denumire = parti[1].trim();
            a.ziua1 = Double.parseDouble(parti[2].trim());
            a.ziua2 = Double.parseDouble(parti[3].trim());
            a.ziua3 = Double.parseDouble(parti[4].trim());
            a.ziua4 = Double.parseDouble(parti[5].trim());
            a.ziua5 = Double.parseDouble(parti[6].trim());

            actiuni.add(a);
        }
        br.close();
        double valoare = 0.0;
        String simbol = null;
        for(Actiune a : actiuni) {
            if(((a.ziua5 - a.ziua1) * 100 / a.ziua1) > valoare) {
                valoare = (a.ziua5 - a.ziua1) * 100 / a.ziua1;
                simbol = a.simbol;
            }
        }
        System.out.println("========== CERINTA 1 =========");
        System.out.println(String.format("%s - crestere %.2f%%", simbol, valoare));
    }

    public static void cerinta2() throws Exception {
        FileReader fr = new FileReader("S203_Tranzactii.json");
        JSONArray arr = new JSONArray(new JSONTokener(fr));

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Tranzactie t = new Tranzactie();

            t.data = obj.getString("Date");
            t.directie = obj.getString("Direction");
            t.simbol = obj.getString("Symbol");
            t.cantitate = obj.getInt("Quantity");
            t.pret = obj.getDouble("Price");

            tranzactii.add(t);
        }
        fr.close();
        tranzactii.sort((a,b)->Double.compare(b.cantitate * b.pret, a.cantitate * a.pret));

        System.out.println("========= CERINTA 2 =========");
        for(int i = 0; i < 5; i++){
            Tranzactie t = tranzactii.get(i);
            System.out.println(String.format("%-10s %-5s %-5s: %-5d x %-5.2f RON = %-5.2f RON", t.data, t.directie, t.simbol, t.cantitate, t.pret, t.cantitate * t.pret));
        }
    }

    public static void cerinta3() throws Exception {
        actiuni.sort((a,b)->a.simbol.compareTo(b.simbol));
        Map<String, Integer> actiuniCumparate = new HashMap<>();
        Map<String, Integer> actiuniVandute = new HashMap<>();
        for (Tranzactie t : tranzactii) {
            if(t.directie.equals("Buy")) {
                actiuniCumparate.put(t.simbol, actiuniCumparate.getOrDefault(t.simbol, 0) + t.cantitate);
            } else if(t.directie.equals("Sell")) {
                actiuniVandute.put(t.simbol, actiuniVandute.getOrDefault(t.simbol, 0) + t.cantitate);
            }
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("raport.txt"));
        bw.write("========= CERINTA 3 =========\n");
        for(Actiune a : actiuni) {
            bw.write(a.simbol + "," + a.denumire + "," + actiuniVandute.get(a.simbol) + "," + actiuniCumparate.get(a.simbol) + "\n");
        }
        bw.close();

    }

    public static void main(String[] args) throws Exception {
        cerinta1();
        cerinta2();
        cerinta3();
    }
}

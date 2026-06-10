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
    public static List<Produs> produse = new ArrayList<>();
    public static List<Tranzactie> tranzactii = new ArrayList<>();
    public static void cerinta1() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("produse.txt"));
        String linie;
        while((linie = br.readLine()) != null) {
            String[] parti = linie.split(",");
            Produs p = new Produs();
            p.cod = Integer.parseInt(parti[0].trim());
            p.denumire = parti[1].trim();
            p.pret = Double.parseDouble(parti[2].trim());

            produse.add(p);
        }
        System.out.println("===== CERINTA 1 =====");
        System.out.println(produse.size());
    }

    public static void cerinta2() throws Exception {
        produse.sort((a,b)->a.denumire.compareTo(b.denumire));
        System.out.println("===== CERINTA 2 =====");
        for(Produs p : produse) {
            System.out.println(String.format("%-5d %-25s %.2f", p.cod, p.denumire, p.pret));
        }
    }

    public static void cerinta3() throws Exception {
        FileReader fr = new FileReader("tranzactii.json");
        JSONArray arr = new JSONArray(new JSONTokener(fr));

        for(int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Tranzactie t = new Tranzactie();

            t.codProdus = obj.getInt("codProdus");
            t.cantitate = obj.getInt("cantitate");
            t.tip = obj.getString("tip");

            tranzactii.add(t);
        }
        fr.close();
        Map<Integer, Integer> tranzactiiPerProdus = new HashMap<>();
        for(Tranzactie t : tranzactii) {
            tranzactiiPerProdus.put(t.codProdus, tranzactiiPerProdus.getOrDefault(t.codProdus, 0) + 1);
        }
        produse.sort((a,b)->tranzactiiPerProdus.get(b.cod) - tranzactiiPerProdus.get(a.cod));

        BufferedWriter bw = new BufferedWriter(new FileWriter("date/subiect1/lista.txt"));
        bw.write("Denumire Produs         Numar tranzactii \n");
        for(Produs p : produse) {
            bw.write(String.format("%-31s %d \n", p.denumire, tranzactiiPerProdus.get(p.cod)));
        }
        bw.close();

    }

    public static void cerinta4() throws Exception {
        Map<Integer, Integer> stocuri = new HashMap<>();
        for(Tranzactie t : tranzactii) {
            int stocCurent = stocuri.getOrDefault(t.codProdus, 0);
            if(t.tip.equals("intrare")) {
                stocuri.put(t.codProdus, stocCurent + t.cantitate);
            } else if (t.tip.equals("iesire")) {
                stocuri.put(t.codProdus, stocCurent - t.cantitate);
            }
        }

        System.out.println("===== CERINTA 4 =====");
        int valoare = 0;
        for(Produs p : produse) {
            System.out.println(String.format("%-30s %-10d", p.denumire, stocuri.get(p.cod)));
            valoare += stocuri.get(p.cod);
        }
        System.out.println("Valoare totala: " + valoare);
    }

    public static void main(String[] args) throws Exception {
        cerinta1();
        cerinta2();
        cerinta3();
        cerinta4();
    }
}

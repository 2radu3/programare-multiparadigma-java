import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.beans.Introspector;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static List<Articol> articole = new ArrayList<>();
    public static List<Evaluare> evaluari = new ArrayList<>();
    public static void cerinta1() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("articole.txt"));
        String linie;
        while((linie = br.readLine()) != null) {
            String[] parti = linie.split(",");

            Articol a = new Articol();
            a.cod = Integer.parseInt(parti[0].trim());
            a.autor = parti[1].trim();
            a.acronim = parti[2].trim();

            articole.add(a);
        }
        br.close();
        System.out.println("===== CERINTA 1 =====");
        System.out.println(articole.size());
    }

    public static void cerinta2() throws Exception {
        FileReader fr = new FileReader("evaluari.json");
        JSONArray arr = new JSONArray(new JSONTokener(fr));

        for(int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Evaluare e = new Evaluare();
            e.codEvaluator = obj.getInt("Cod evaluator");
            e.codArticol = obj.getInt("Cod articol");
            e.nivel = obj.getInt("Nivel stiintific");
            e.notorietate = obj.getInt("Notorietate");
            e.citari = obj.getInt("Citari");

            evaluari.add(e);
        }
        fr.close();

        Map<Integer, Integer> evaluariPerArticol = new HashMap<>();
        for(Evaluare e : evaluari) {
            evaluariPerArticol.put(e.codArticol, evaluariPerArticol.getOrDefault(e.codArticol, 0) + 1);
        }

        System.out.println("===== CERINTA 2 =====");
        for(Map.Entry<Integer, Integer> entry : evaluariPerArticol.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

    }

    public static void cerinta3() throws Exception {
        Map<Integer, Integer> elemente = new HashMap<>();
        Map<Integer, Integer> elemente2 = new HashMap<>();
        for (Evaluare e : evaluari) {
            elemente.put(e.codArticol, elemente.getOrDefault(e.codArticol, 0) + e.nivel + e.notorietate + e.citari);
            elemente2.put(e.codArticol, elemente2.getOrDefault(e.codArticol, 0) + 1);
        }

        articole.sort((a, b)-> {double mediaA = (double)elemente.get(a.cod) / elemente2.get(a.cod);
        double mediaB = (double)elemente.get(b.cod) / elemente2.get(b.cod);
        return Double.compare(mediaB, mediaA);});

        BufferedWriter bw = new BufferedWriter(new FileWriter("jurnal.txt"));
        for(Articol a : articole) {
            bw.write(a.cod + " " + a.autor + " " + elemente.get(a.cod) / elemente2.get(a.cod) + "\n");
        }
        bw.close();

    }

    public static void main(String[] args) throws Exception {
        cerinta1();
        cerinta2();
        cerinta3();
    }
}

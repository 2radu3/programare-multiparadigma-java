import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.util.*;

public class Main {
    public static List<Vagon> listaVagoane = new ArrayList<>();
    public static List<Tren> trenuri = new ArrayList<>();

    public static void citireVagoane() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("S202_Vagoane.csv"));
        String linie;
        while((linie = br.readLine()) != null) {
            linie = linie.trim();

            String parti[] = linie.split(",");
            Vagon v = new Vagon();
            v.id = Integer.parseInt(parti[0].trim());
            v.tip = parti[1].trim();
            v.capacitate = Integer.parseInt(parti[2].trim());

            listaVagoane.add(v);
        }
        br.close();
    }

    public static void capacitateTotala() {
        Map<String, Integer> capacitati = new HashMap<>();

        for(Vagon v : listaVagoane) {
            if(capacitati.containsKey(v.tip)) {
                capacitati.put(v.tip, capacitati.get(v.tip) + v.capacitate);
            } else {
                capacitati.put(v.tip, v.capacitate);
            }
        }

        System.out.println("======= CERINTA 1 =======");
        for(Map.Entry<String, Integer> entry : capacitati.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    public static void citireTrenuri() throws Exception{
        FileReader fr = new FileReader("S202_Trenuri.json");
        JSONArray arr = new JSONArray(new JSONTokener(fr));

        for(int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Tren t = new Tren();

            t.codTren = obj.getInt("CodTren");
            t.tipTren = obj.getString("TipLocomotiva");

            JSONArray vagoane = obj.getJSONArray("Vagoane");
            t.vagoane = new ArrayList<>();
            for(int j = 0; j < vagoane.length(); j++) {
                int codVagon = vagoane.getInt(j);
                for(Vagon v : listaVagoane) {
                    if(v.id == codVagon) {
                        t.vagoane.add(v);
                        break;
                    }
                }
            }
            trenuri.add(t);
        }
        fr.close();

        trenuri.sort((a, b) -> a.codTren - b.codTren);
        System.out.println("======= CERINTA 2 =======");
        for(Tren t : trenuri) {
            System.out.println(t.codTren + " " + t.tipTren + " " + t.vagoane.size());
        }
    }

    public static void cerinta3() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti tipul vagonului: ");
        String tipCautat = scanner.nextLine().trim();

        System.out.println("====== CERINTA 3 ======");
        System.out.println("Tip: " + tipCautat);

        for(Tren t : trenuri) {
            int capacitateTotala = 0;

                for(Vagon v : t.vagoane) {
                    if(v.tip.equals(tipCautat)) {
                        capacitateTotala += v.capacitate;
                    }
                }
            if(capacitateTotala > 0) {
                System.out.println(t.codTren + " " + t.tipTren + " " + capacitateTotala + " tone - " + tipCautat);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        citireVagoane();
        capacitateTotala();
        citireTrenuri();
        cerinta3();

    }
}
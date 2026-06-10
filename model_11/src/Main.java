import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static List<Achizitie> achizitii = new ArrayList<>();
    public static void cerinta1() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("achizitii.txt"));
        String linie;
        while((linie = br.readLine()) != null) {
            String[] parti = linie.split(",");
            String cod = parti[0].trim();
            int an = Integer.parseInt(parti[1].trim());
            int luna = Integer.parseInt(parti[2].trim());
            int zi = Integer.parseInt(parti[3].trim());
            int cantitate = Integer.parseInt(parti[4].trim());
            double pret = Double.parseDouble(parti[5].trim());

            Achizitie a = new Achizitie(cod, an, luna, zi, cantitate ,pret);
            achizitii.add(a);
        }

        for(Achizitie a : achizitii) {
            if(a.zi > 15 && a.cantitate > 100) {
                System.out.println(a.toString());
            }
        }
    }

    public static void cerinta2() throws Exception {
        Map<String, List<Achizitie>> grupate = achizitii.stream().collect(Collectors.groupingBy(a -> a.getCod()));

        grupate.entrySet().stream().map(entry -> {
            String cod = entry.getKey();
            List<Achizitie> lista = entry.getValue();
        int nrAchizitii = lista.size();
        double valoareTotala = lista.stream().mapToDouble(a->a.valoare()).sum();
        return cod + " -> " + nrAchizitii + " achizitii, valoare totala " + valoareTotala + " Lei";}).sorted().forEach(System.out::println);
    }

    public static void main(String[] args) throws Exception {
        cerinta1();
        cerinta2();
    }
}

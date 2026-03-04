package seminar.seminar2.g1061;

import java.text.SimpleDateFormat;

public class Main {
    public static SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy");
    public static void main(String[] args) {
        System.out.println("Tema 2.2");
        try {
            Adresa a1 = new Adresa("Brasov", "Brasov", "Bronzului", "39a");
//        System.out.println(a1);
            MijlocFix m1 = new MijlocFix("Cladire sediu central", 111L, 500000,
                    fmt.parse("10.10.2020"), new Locatie("Sediu Central", a1), Categorie.CONSTRUCTII, 100);

            System.out.println(m1);
            System.out.println("Amortizare: " + m1.amortizare());

            MijlocFix m2 = new MijlocFix(111L);
            System.out.println(m1.equals(m2));

            MijlocFix clona = (MijlocFix) m1.clone();
            m1.getLocatie().getAdresa().setJudet("Maramures"); //modifica judetul si in clona si in m1
            System.out.println("Clona: ");
            System.out.println(clona);
            System.out.println("Mijloc fix: ");
            System.out.println(m1);

        }
        catch (Exception ex) {
            System.err.println(ex);
        }
    }
}

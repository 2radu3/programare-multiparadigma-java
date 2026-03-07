package seminar.seminar2.g1061;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;


public class Main {
    public static SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy");

    private static MijlocFix[] mijloaceFixe = new MijlocFix[0];

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

            citireDate("MijloaceFixe.csv");
            for(MijlocFix m:mijloaceFixe) {
                System.out.println(m);
            }

        }
        catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public static void citireDate(String numeFisier) {
        try(Scanner scanner = new Scanner(new File(numeFisier))) {
            while(scanner.hasNextLine()){
                String[] t = scanner.nextLine().trim().split(",");
                MijlocFix mijlocFix = new MijlocFix();
                mijlocFix.setDenumire(t[0].trim());
                mijlocFix.setNrInventar(Long.parseLong(t[1].trim()));
                mijlocFix.setValoare(Double.parseDouble(t[2].trim()));
                mijlocFix.setDataAchizitie(fmt.parse(t[3].trim()));
                mijlocFix.setCategorie(Categorie.valueOf(t[4].trim().toUpperCase()));
                mijlocFix.setDurataNormata(Integer.parseInt(t[5].trim()));
                t = scanner.nextLine().trim().split(",");
                Locatie locatie = new Locatie();
                locatie.setDenumire(t[0].trim());
                Adresa adresa = new Adresa(t[1].trim(),t[2].trim(),t[3].trim(),t[4].trim());
                locatie.setAdresa(adresa);
                mijlocFix.setLocatie(locatie);
                mijloaceFixe = Arrays.copyOf(mijloaceFixe,mijloaceFixe.length+1);
                mijloaceFixe[mijloaceFixe.length-1] = mijlocFix;
            }
        }
        catch(Exception ex) {
            System.err.println(ex);
        }
    }


}

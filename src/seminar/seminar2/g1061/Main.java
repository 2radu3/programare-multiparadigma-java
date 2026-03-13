package seminar.seminar2.g1061;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy");
    private static MijlocFix[] mijloaceFixe = new MijlocFix[0];

    public static List<MijlocFix> lista = new ArrayList<>();// folosesc referinta de tip list

    public static void main(String[] args) {
        System.out.println("Tema 2.2");

        try {
            /*
            Adresa a1 = new Adresa("Brasov", "Brasov", "Bronzului", "39a");
//        System.out.println(a1);
            MijlocFix m1 = new MijlocFix(
                    "Cladire sediu central", 111L, 500000,
                    fmt.parse("10.10.2020"),
                    new Locatie("Sediu Central", a1),
                    Categorie.CONSTRUCTII, 100
            );
            System.out.println(m1);
            System.out.println("Amortizare:"+m1.amortizare());

            MijlocFix m2 = new MijlocFix(111L);
            System.out.println(m1.equals(m2));

            MijlocFix clona = (MijlocFix) m1.clone();

            m1.getLocatie().getAdresa().setJudet("Maramures");
            System.out.println("Clona:");
            System.out.println(clona);
            System.out.println("Mijloc fix:");
            System.out.println(m1);
*/

            citireDate("MijloaceFixe.csv");
            for (MijlocFix m : mijloaceFixe) {
                System.out.println(m);
            }

            creareLista();
            System.out.println("Lista mijloacelor fixe:");
            for (MijlocFix mijlocFix : lista) {
                System.out.println(mijlocFix);
            }

            // Sortare dupa data achizitie
            Collections.sort(lista);
            System.out.println("Lista sortata dupa data:");
            for (MijlocFix mijlocFix : lista) {
                System.out.println(mijlocFix);
            }
            // Sortare dupa numar inventar
            // ce sunt clasele anonime si cum se instantiaza un obiect dintr o clasa anonima?
            // de regula e vb de clasa care implmenteaza o clasa - cu new [] si acolo implementez interfata
            // sau pot cu new object [] si pot sa mi definesc o clasa

            // clasa anonime - clase care new numeInterfata() {implement interfasta } sau pot cu NewObject()
            // Sortare dupa nr Inventar
            // aici face sortare pe o lista dupa un criteriu altul cel implementat decat a clasei, Comparator
            Collections.sort(lista, new Comparator<MijlocFix>() {

                @Override
                public int compare(MijlocFix m1, MijlocFix m2) {
                    return Long.compare(m1.nrInventar, m2.nrInventar);
                }
            });

            System.out.println("Lista sortata dupa nr inventar:");
            for (MijlocFix mijlocFix : lista) {
                System.out.println(mijlocFix);
            }

            Comparator<MijlocFix> comparatorValoare = new Comparator<MijlocFix>() {
                @Override
                public int compare(MijlocFix m1, MijlocFix m2) {
                    return Double.compare(m1.valoare, m2.valoare);
                }
            };

//            Sortare dupa valoare
            Collections.sort(lista, comparatorValoare);
            System.out.println("\nLista sortata dupa valoare:");
            for (MijlocFix mijlocFix : lista) {
                System.out.println(mijlocFix);
            }

//            Selectie dupa numar inventar
            long nrInventar = 3L;
            int k = lista.indexOf(new MijlocFix(nrInventar));
            if (k == -1) {
                System.out.println("\nNu exista mijloc fix cu nr inv " + nrInventar);
            } else {
                System.out.println("\nMijlocul fix cautat:\n" + lista.get(k));
            }

//            Selectie dupa valoare
            MijlocFix mijlocFix = new MijlocFix();
            mijlocFix.setValoare(50001);
            Collections.sort(lista, comparatorValoare);
            k = Collections.binarySearch(lista, mijlocFix, comparatorValoare);
            if (k >= 0) {
                System.out.println("\nMijlocul fix cautat:\n" + lista.get(k));
            } else {
                System.out.println("Nu exista mijloc fix cu valoare " + mijlocFix.getValoare());
                System.out.println("Pozitie de inserare:" + (-k - 1));
            }

//            Printare uzura
            printare("UzuraMf.csv");

//            Salvare lista - Serializare
//            salvare("mf.dat");
//            System.out.println("Date salvate ...");
//            Restaurare
            restaurare("mf.dat");
            System.out.println("\nLista restaurata:");
            for (MijlocFix m : lista) {
                System.out.println(m);
            }


        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public static void citireDate(String numeFisier) {
        try (Scanner scanner = new Scanner(new File(numeFisier))) {
            while (scanner.hasNextLine()) {
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
                Adresa adresa = new Adresa(t[1].trim(), t[2].trim(), t[3].trim(), t[4].trim());
                locatie.setAdresa(adresa);
                mijlocFix.setLocatie(locatie);
                mijloaceFixe = Arrays.copyOf(mijloaceFixe, mijloaceFixe.length + 1);
                mijloaceFixe[mijloaceFixe.length - 1] = mijlocFix;
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
    // nu fac duplicat doar copiez adresele sau cv
    // cate obiecte voi avea in memorie aici? de ce tot 5? pentru ca am copiat referintele, nu am facut cu for

    public static void creareLista() {
        lista.addAll(Arrays.asList(mijloaceFixe)); // costruieste o lista si sunt adaugate elementele din lista mea
    }

    private static void printare(String numeFisier) {
        try (PrintWriter fout = new PrintWriter(numeFisier)) {
            for (MijlocFix mijlocFix : lista) {
                fout.println(mijlocFix.nrInventar + "," + mijlocFix.denumire + "," + mijlocFix.calculUzura());
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private static void salvare(String numeFisier) {
        try (ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(numeFisier))) {
            for (MijlocFix mijlocFix : lista) {
                fout.writeObject(mijlocFix);
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private static void restaurare(String numeFisier) {
        try (FileInputStream in = new FileInputStream(numeFisier);
             ObjectInputStream fin = new ObjectInputStream(in)) {
            lista.clear();
            while (in.available() != 0) {
                lista.add((MijlocFix) fin.readObject());
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
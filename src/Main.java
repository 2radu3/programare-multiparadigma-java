package seminar.seminar_05.g1061;

import seminar.seminar2.g1061.MijlocFix;
import seminar.seminar2.g1061.Locatie;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {

        try {
            seminar.seminar2.g1061.Main.citireDate("MijloaceFixe.csv");
            seminar.seminar2.g1061.Main.creareLista();
            List<MijlocFix> lista = seminar.seminar2.g1061.Main.lista;
//            for(MijlocFix mijlocFix:lista) {
//                System.out.println(mijlocFix);
//            }

            // Afisare prin clasa anonima (interfata functionala explicita)
            System.out.println("\nLista m.f...");
//            lista.forEach(new Consumer<MijlocFix>() { //consumer e o interfata functionala are o singura interfrafata abstracta
//                @Override
//                public void accept(MijlocFix mijlocFix) {
//                    System.out.println(mijlocFix);
//                }
//            });

            // Afisare prin lambda
            lista.forEach(mijlocFix -> System.out.println(mijlocFix));

            // Afisare prin referinta la metoda
            lista.forEach(System.out::println);

            // Cerinta 1
            // Filtrare dupa valoare
            double vMin = 50000, vMax = 250000;
//            List<MijlocFix> cerinta1 = lista.stream().filter(
//                    new Predicate<MijlocFix>() {
//                        @Override
//                        public boolean test(MijlocFix mijlocFix) {
//                            return mijlocFix.getValoare() >= vMin && mijlocFix.getValoare() <= vMax;
//                        }
//                    }
//            ).toList(); //filter e intermediar returneaza tot stream si toList e finala

            List<MijlocFix> cerinta1 = lista.stream().filter(mijlocFix -> mijlocFix.getValoare() >= vMin && mijlocFix.getValoare() <= vMax).toList();

            System.out.println("\nMijloace fixe intre [" + vMin + ", " + vMax + " ]");
            cerinta1.forEach(System.out::println);

            // Cerinta 2
            // Selecția mijloacelor fixe de la o anumită locație
            String denumireLocatie = "Punct de lucru Covasna";
            List<MijlocFix> cerinta2 = lista.stream().filter(mijlocFix -> mijlocFix.getLocatie().getDenumire().equals(denumireLocatie)).toList();
            System.out.println(("Mijloace fixe din locatia " + denumireLocatie + ":"));
            cerinta2.forEach(System.out::println);

            // Cerinta 3
            Date dataLimita = seminar.seminar2.g1061.Main.fmt.parse("01.01.2013");

            List<MijlocFix> cerinta3 = lista.stream().filter(mijlocFix -> mijlocFix.getDataAchizitie().before(dataLimita) ).toList();
            System.out.println(("Mijloace fixe achizitionate inainte de: " + seminar.seminar2.g1061.Main.fmt.format(dataLimita) + ":"));
            cerinta3.forEach(System.out::println);



        }
        catch(Exception ex) {
            System.err.println(ex);
        }
    }
}
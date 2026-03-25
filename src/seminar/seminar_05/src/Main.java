package seminar.seminar_05.src;

import seminar.seminar2.g1061.Categorie;
import seminar.seminar2.g1061.ElementPatrimonial;
import seminar.seminar2.g1061.MijlocFix;
import seminar.seminar2.g1061.Locatie;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

            // Seminar 6
            // Cerinta 4
//            List<MijlocFix> cerinta4 = lista.stream().sorted(new Comparator<MijlocFix>() {
//                @Override
//                public int compare(MijlocFix o1, MijlocFix o2) {
//                    return Double.compare(o1.calculUzura(),o2.calculUzura());
//                }
//            }).toList();
            List<MijlocFix> cerinta4 = lista.stream().sorted((o1,o2)->Double.compare(o1.calculUzura(),o2.calculUzura())).toList(); //sau asa
            System.out.println("Lista sortata dupa uzura");
            cerinta4.forEach(mijlocFix -> System.out.println(mijlocFix.getDenumire()+","+mijlocFix.calculUzura()));

            // Cerinta 5
            Set<String> cerinta5 = lista.stream().map(mijlocFix -> mijlocFix.getLocatie().getDenumire()).collect(Collectors.toSet());
            System.out.println("\nLista locatiilor: ");
            cerinta5.forEach(System.out::println);

            // Cerinta 6
            Map<Long, Locatie> cerinta6 = lista.stream().collect(Collectors.toMap(MijlocFix::getNrInventar, MijlocFix::getLocatie));
            System.out.println("\nColectare locatii pe numere de inventar: ");
            cerinta6.forEach((nrinv,locatie)-> System.out.println(nrinv+","+locatie));

            // Cerinta 7
            Map<Categorie, List<MijlocFix>> cerinta7 = lista.stream().collect(Collectors.groupingBy(MijlocFix::getCategorie));
            System.out.println("\nGrupare mijloace fixe pe categorii");
            cerinta7.forEach(((categorie, mijloaceFixe) -> {
                System.out.println("Categorie: "+ categorie);
                mijloaceFixe.forEach(System.out::println);
            }));

            // Cerinta 8
            Map<Categorie, List<String>> cerinta8 = lista.stream().collect(Collectors.groupingBy(MijlocFix::getCategorie,Collectors.mapping(MijlocFix::getDenumire,Collectors.toList())));
            System.out.println("\nGrupare mijloace fixe pe categorii: ");
            cerinta8.forEach(((categorie,denumiri)-> System.out.println(categorie+","+denumiri)));

            // Cerinta 9
            Map <String, Double> cerinta9 = lista.stream().collect(Collectors.groupingBy(mijlocFix -> mijlocFix.getLocatie().getDenumire(), Collectors.averagingDouble(MijlocFix::calculUzura)));
            System.out.println("\nUzura medie pe locatii:");
            cerinta9.forEach((denumirel,uzura)-> System.out.println(denumirel+","+uzura));

//            class Info{
//                //si punem aici alea de mai jos
//            }

            // Cerinta 10
            Map<Long,?> cerinta10 = lista.stream()
                    .collect(Collectors.toMap(
                            MijlocFix::getNrInventar,
                            mijlocFix -> new Object(){
                                private String denumire = mijlocFix.getDenumire();
                                private Date dataA = mijlocFix.getDataAchizitie();

                                @Override
                                public String toString() {
                                    return denumire+","+(dataA==null?"": seminar.seminar2.g1061.Main.fmt.format(dataA));
                                }
                            }
                    ));
            System.out.println("\nColectare informatii pe mijloc fix (numar inventar)");
            cerinta10.forEach((nrinv,info)-> System.out.println(nrinv+":"+info));

            // Cerinta 11
//            Map<Categorie,List<Long>> cerinta11 = lista.stream()
//                    .collect(new Supplier<Map<Categorie, List<Long>>>() {
//                        @Override
//                        public Map<Categorie, List<Long>> get() {
//                            return new HashMap<>();
//                        }
//                    }, new BiConsumer<Map<Categorie, List<Long>>, MijlocFix>() {
//                        @Override
//                        public void accept(Map<Categorie, List<Long>> map, MijlocFix mijlocFix) {
//                            Categorie categorie = mijlocFix.getCategorie();
//                            if (map.containsKey(categorie)){
//                                map.get(categorie).add(mijlocFix.getNrInventar());
//                            } else {
//                                List<Long> numereInventar = new ArrayList<>();
//                                numereInventar.add(mijlocFix.getNrInventar());
//                                map.put(categorie,numereInventar);
//                            }
//                        }
//                    }, new BiConsumer<Map<Categorie, List<Long>>, Map<Categorie, List<Long>>>() {
//                        @Override
//                        public void accept(Map<Categorie, List<Long>> map1, Map<Categorie, List<Long>> map2) {
//                            map1.putAll(map2);
//                        }
//                    });
            Map<Categorie, List<Long>> cerinta11 = lista.stream()
                    .collect(
                            HashMap::new,
                            (map, mijlocFix) -> {
                                Categorie categorie = mijlocFix.getCategorie();
                                if (map.containsKey(categorie)) {
                                    map.get(categorie).add(mijlocFix.getNrInventar());
                                } else {
                                    List<Long> numereInventar = new ArrayList<>();
                                    numereInventar.add(mijlocFix.getNrInventar());
                                    map.put(categorie, numereInventar);
                                }
                            },
                            HashMap::putAll
                    );
            System.out.println("\nColectare numere inventar pe categorii:");
            cerinta11.forEach(((categorie, numere) -> System.out.println(categorie + ":" + numere)));




        }
        catch(Exception ex) {
            System.err.println(ex);
        }
    }
}
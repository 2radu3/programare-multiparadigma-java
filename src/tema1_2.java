import java.util.Scanner;
import java.util.DoubleSummaryStatistics;
import java.util.Arrays;
import java.io.File;

public class tema1_2 {
    public static void main(String[] args) throws Exception {
        if (args.length!=3){
            throw new Exception("Numar invalid de argumente");
        }

        int n = Integer.parseInt(args[0]);
        double a = Double.parseDouble(args[1]);

        double[][] x = initializare(n, a, args[2]);

        x = adaugare(x);
        x = eliminare(x);

        print(x);

        calculMedii("note.txt");
    }
//1. Să se definească o matrice diagonală (superior sau inferior diagonală) de tip real cu n linii și n
//coloane. Elementele aflate sub sau peste diagonala principală sunt inițializate cu a. Valorile n și a
//sunt furnizate prin linie de comandă (parametri metodei statice main). Tot prin linie de comandă
//va fi furnizată și informația care indică tipul: s/S - superior diagonală, i/I - inferior diagonală.
    private static double[][] initializare(int n, double a, String tip) throws Exception {
        double[][] x = new double[n][n];
        if(tip.equalsIgnoreCase("s")) {
            for(int i = 0; i < n; i++){
                Arrays.fill(x[i],i+1,n,a);
            }
        } else {
            if(tip.equalsIgnoreCase("i")){
                for(int i = 1; i < n; i++){
                    Arrays.fill(x[i],0,i,a);
                }
            } else {
                throw new Exception ("Tip matrice eronat!");
            }
        }
        return x;
    }

    private static void print (double[][] x) {
        for(double[] linie:x){
            System.out.println(Arrays.toString(linie));
        }
    }

    //2.Să se implementeze metode statice pentru adăugare/eliminare de linii/coloane la matrice
    private static double[][] adaugare(double[][] x) throws Exception {
        if(x.length<2){
            throw new Exception("Matricea nu are cel putin 2 linii");
        }
        String tip = x[1][0]!=0?"i":"s";
        double a = x[1][0] + x[0][1];
        return initializare(x.length+1, a, tip);
    }

    private static double[][] eliminare(double[][] x) throws Exception {
        if (x.length<2){
            throw new Exception("Matricea nu are cel putin 2 linii!");
        }
        String tip = x[1][0]!=0?"i":"s";
        double a = x[0][1] + x[1][0];
        return initializare(x.length-1, a, tip);

    }

    //3.Să se calculeze și să se afișeze mediile pe grupe și media generală la disciplina Programare
    //multiparadigmă - Java. Notele vor fi citite dintr-un fișier text input.txt prin redirectarea inputului
    //standard. Va fi folosită clasa Scanner. Pe prima linie a fișierului se află n, numărul de grupe. Pe
    //următoarele n linii se află notele pe grupe. Pe fiecare linie, prima valoare este numărul grupei iar
    //următoarele valori, notele. Valorile sunt despărțite prin virgulă.
    private static void calculMedii (String numeFisier) throws Exception {
        File fisier = new File(numeFisier);

        try(Scanner scanner = new Scanner(fisier)) {
            int n = Integer.parseInt(scanner.nextLine());
            DoubleSummaryStatistics sumator2 = new DoubleSummaryStatistics();
            while (scanner.hasNextLine()) {
                String[] linie = scanner.nextLine().trim().split(",");

                DoubleSummaryStatistics sumator = new DoubleSummaryStatistics();
                for (int i = 1; i < linie.length; i++) {
                    sumator.accept(Double.parseDouble(linie[i].trim()));
                    sumator2.accept(Double.parseDouble(linie[i].trim()));
                }
                System.out.println(linie[0] + "," + sumator.getAverage());
            }
            System.out.println(sumator2.getAverage());
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}









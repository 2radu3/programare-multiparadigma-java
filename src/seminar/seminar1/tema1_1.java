package seminar.seminar1;

import java.util.Arrays;
import java.io.File;
import java.util.Scanner;

public class tema1_1 {
    public static void main(String[] args) throws Exception {
        if(args.length < 2 || args.length % 2 == 1){
            throw new Exception("Prea putine argumente!!");
        }

        double[][] x = initializare(args);

        x = inserare(x, 2, 6, 9, args);
        x = eliminare(x, 3);
        schimbareValoare(x, 2, 1);
        adaugaElemente(x, 1);
        //print(x);

        int[][][] z = citireDate("input.txt");
        afisare2(z);

    }

    private static double[][] initializare(String[] args) {
        double[][] x = new double[args.length/2][];
        String[] pare =  new String[args.length/2];
        String[] impare = new String[args.length/2];
        int contorPare = 0;
        int contorImpare = 0;
        for(int i = 0; i < args.length; i++){
            if(i%2==0){
                pare[contorPare] = args[i];
                contorPare++;
            } else {
                impare[contorImpare] = args[i];
                contorImpare++;
            }
        }
        for(int i = 0; i < args.length / 2; i++) {
            x[i] = new double[Integer.parseInt(pare[i])];
            Arrays.fill(x[i],0,Integer.parseInt(pare[i]),Integer.parseInt(impare[i]));

        }
        return x;
    }

    private static void print(double[][] x){
        for (double[] linie: x) {
            System.out.println(Arrays.toString(linie));
        }
    }

    private static double[][] eliminare(double[][] x, int poz) throws Exception {
        if(poz > x.length || poz < 0) {
            throw new Exception("Pozitia nu e buna!!");
        }
        double[][] x2 = new double[x.length - 1][];
        //for(int i = 0; i < poz; i++) { }
        System.arraycopy(x, 0, x2, 0, poz);
        System.arraycopy(x, poz + 1, x2, poz, x.length - poz - 1);
        return x2;
    }

    private static double[][] inserare(double[][] x, int poz, int lungime, int valoare, String[] args) throws Exception {
        if(poz > x.length) {
            throw new Exception("Pozitia este prea mare!");
        }
        double[][] x2 = new double[x.length + 1][];
        for(int i = 0; i < poz; i++) {
            x2[i] = x[i];
        }
        x2[poz] = new double[lungime];
        Arrays.fill(x2[poz], valoare);
        for(int i = poz; i < x.length; i++) {
            x2[i + 1] = x[i];
        }
        return x2;
    }

    private static void schimbareValoare(double[][] x, int poz, double valoare) throws Exception {
        if (poz > x.length || poz < 0) {
            throw new Exception("Pozitia nu este buna!!");
        }
        for(int i = 0; i < x.length; i++) {
            if(i == poz) {
                for (int j = 0; j < x[i].length; j++) {
                    x[i][j] = valoare;
                }
            }
        }
    }

    private static void adaugaElemente(double[][] x, int poz) throws Exception {
        if(poz < 0 || poz > x.length) {
            throw new Exception("Pozitia nu este buna");
        }
        double n = x[poz][0];
        x[poz] = new double[x[poz].length + 1];
        Arrays.fill(x[poz], n);
    }

    private static int[][][] citireDate(String numeFisier) throws Exception {
        File fisier = new File(numeFisier);

        Scanner scanner = new Scanner(fisier);
        int n = Integer.parseInt(scanner.nextLine());
        int[][][] z = new int[n][][];
        for(int i = 0; i < z.length; i++) {
            if(scanner.hasNextLine()) {
                String[] linie = scanner.nextLine().trim().split(",");
                z[i] = new int [linie.length][];
                for(int j = 0; j < linie.length; j++) {
                    int coloane = Integer.parseInt(linie[j]);
                    z[i][j] = new int[coloane];
                    Arrays.fill(z[i][j], i);
                }
            }
        }
        return z;
    }

    private static void afisare2(int[][][] z) {
        for(int[][] matrice:z) {
            for(int[] linie:matrice) {
                System.out.println(Arrays.toString(linie));
            }
        }
    }

}
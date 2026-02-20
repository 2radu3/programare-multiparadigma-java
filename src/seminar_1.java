import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.Scanner;

public class practice_1 {
    public static void main(String[] args) throws Exception {
     //   System.out.println("Seminar 1");
     //   for (String element:args){
     //       System.out.println(element);
     //     }
        if (args.length != 3) {
            throw new Exception("Numar invalid de parametri in linia de comanda!");
        }

        int n = Integer.parseInt(args[0]);
        double v = Double.parseDouble(args[1]);

        double[][] x = initializare(n,v,args[2]);

        //System.out.println(x);

        print(x, "Matricea initiala");

        x = adaugare(x);
        print(x, "Matricea extinsa");

        x = eliminare(x);
        print(x, "Matricea dupa eliminare");

        calculMedii();

    }

    private static void print(double[][] x, String mesaj){
        System.out.println(mesaj);
        for(double[] linie:x){
            System.out.println(Arrays.toString(linie));
        }
    }

    private static double[][] eliminare(double[][] x) throws Exception{
        if(x.length<3){
            throw new Exception("Matricea nu are cel putin 3 linii/coloane!");
        }
        String tip = x[1][0]!=0?"i":"s";
        double v = x[1][0]+x[0][1];
        return initializare(x.length-1,v,tip);
    }

    private static double[][] adaugare(double[][] x) throws Exception {
        if (x.length<2){
            throw new Exception ("Matricea nu are cel putin 2 linii/coloane");
        }
        String tip = x[1][0]!=0?"i":"s";
        double v = x[1][0] + x[0][1];
        return initializare(x.length+1,v,tip);
    }

    private static double[][] initializare(int n, double v, String tip) throws Exception {
        double[][] x = new double[n][n];
        if(tip.equalsIgnoreCase("i")){
            for(int i = 1; i < n; i++){
                Arrays.fill(x[i],0,i,v);
            }
        } else {
            if(tip.equalsIgnoreCase("s")){
                for(int i = 0; i < n; i++){
                    Arrays.fill(x[i],i+1,n,v);
                }
            } else {
                throw new Exception ("Tip matrice eronat!");
            }
        }
        return x;
    }

    private static void calculMedii(){
        try(Scanner scanner = new Scanner(System.in)){
            System.out.println("Introduceti numerele: ");
            //int n = Integer.parseInt(scanner.nextLine().trim());
            for (int i = 0; i < 1; i++){
                String[] linie = scanner.nextLine().trim().split(",");
                DoubleSummaryStatistics sumatorGrupa = new DoubleSummaryStatistics();
                for (int j = 0; j < linie.length; j++){
                    sumatorGrupa.accept(Double.parseDouble(linie[j].trim()));
                }
                System.out.println(linie[0] + ", " + sumatorGrupa.getAverage());
            }
        }
        catch (Exception e){
            System.err.println(e);
        }
    }



}

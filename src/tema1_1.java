import java.util.Arrays;

public class tema1_1 {
    public static void main(String[] args) throws Exception {
        if(args.length < 2 || args.length % 2 == 1){
            throw new Exception("Prea putine argumente!!");
        }



        double[][] x = initializare(args);

        x = inserare(x, 2, 6, 9, args);
        print(x);
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


}
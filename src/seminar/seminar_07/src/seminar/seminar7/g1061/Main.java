package seminar.seminar7.g1061;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            int n = 10000, m = 10000;
            Double[][] x = new Double[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    x[i][j] = (double) (n * i + j);
                }
            }
            Double[] y = new Double[m], z = new Double[n];
            Arrays.fill(y, 1.0);
//            print(x);

//            Inmumtire pe un singur fir
            Date start = new Date();
            Operatiuni<Double> o = new Operatiuni<Double>() {
                @Override
                public Double init() {
                    return 0.0;
                }

                @Override
                public Double add(Double a, Double b) {
                    return a + b;
                }

                @Override
                public Double mul(Double a, Double b) {
                    return a * b;
                }
            };
            Inmultire<Double> inmultire = new Inmultire<>(x, 0, n - 1, y, z, o);
            Thread firInmultire = new Thread(inmultire);
            firInmultire.start();
            firInmultire.join();
            Date stop = new Date();
            System.out.println("Timp de executie pe un fir:" + (stop.getTime() - start.getTime()));
//            System.out.println(Arrays.toString(z));
            start = new Date();
            List<Thread> fire = new ArrayList<>();
            int nrMaxFire = 10, l1 = 0, l2;
            int pas = n / nrMaxFire + 1;
            while (l1 < n) {
                if (l1 + pas < n) {
                    l2 = l1 + pas;
                } else {
                    l2 = n - 1;
                }
                fire.add(new Thread(new Inmultire<>(x, l1, l2, y, z, o)));
                l1 = l2+1;
            }
            for (Thread fir:fire){
                fir.start();
            }
            for (Thread fir:fire){
                fir.join();
            }
            stop = new Date();
//            System.out.println(Arrays.toString(z));
            System.out.println("Timp de executie pe "+fire.size()+" fire:" + (stop.getTime() - start.getTime()));

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private static void print(Double[][] x) {
        for (Double[] linie : x) {
            System.out.println(Arrays.toString(linie));
        }
    }

}

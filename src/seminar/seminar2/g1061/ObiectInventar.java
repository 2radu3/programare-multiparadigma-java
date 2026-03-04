package seminar.seminar2.g1061;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ObiectInventar extends ElementPatrimonial {
    private String gestionar;

    public ObiectInventar() {
    }

    public ObiectInventar(String denumire, long nrInventar, double valoare, Date dataAchizitie, Locatie locatie, String gestionar) {
        super(denumire, nrInventar, valoare, dataAchizitie, locatie);
        this.gestionar = gestionar;
    }

    public ObiectInventar(long nrInventar) {
        super(nrInventar);
    }

    public String getGestionar() {
        return gestionar;
    }

    public void setGestionar(String gestionar) {
        this.gestionar = gestionar;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + gestionar;
    }

    @Override
    public double calculUzura() {
        Date dataCurenta = new Date();
        long numarZileFunctionare = TimeUnit.MILLISECONDS.toDays(dataCurenta.getTime()-dataAchizitie.getTime());
        return numarZileFunctionare>365?1.0:0.0;
    }

    @Override
    public double amortizare() {
        return calculUzura() * valoare;
    }
}

package seminar.seminar2.g1061;

import java.util.Date;

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


}

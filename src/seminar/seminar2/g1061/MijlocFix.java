package seminar.seminar2.g1061;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MijlocFix extends ElementPatrimonial implements Cloneable {

    private Categorie categorie;
    private int durataNormata;

    public MijlocFix() {
    }

    public MijlocFix(String denumire, long nrInventar, double valoare, Date dataAchizitie, Locatie locatie, Categorie categorie, int durataNormata) throws Exception {
        super(denumire, nrInventar, valoare, dataAchizitie, locatie);
        if (durataNormata <= 0 || durataNormata > 100) {
            throw new Exception("Durata normata in afara limitelor legale!");
        }
        this.categorie = categorie;
        this.durataNormata = durataNormata;
    }

    public MijlocFix(long nrInventar) {
        super(nrInventar);
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public int getDurataNormata() {
        return durataNormata;
    }

    public void setDurataNormata(int durataNormata) throws Exception {
        if (durataNormata <= 0 || durataNormata > 100) {
            throw new Exception("Durata normata in afara limitelor legale!");
        }
        this.durataNormata = durataNormata;
    }

    @Override
    public String toString() {
        return super.toString() + "\n{ " + categorie + ", "+ durataNormata + " } ";

    }

    @Override
    public double calculUzura() {
        Date dataCurenta = new Date();
        long numarZileFunctionare = TimeUnit.MILLISECONDS.toDays(dataCurenta.getTime()-dataAchizitie.getTime());
        return ((double) numarZileFunctionare)/(durataNormata*365);
    }

    @Override
    public double amortizare() {
        return calculUzura()*getValoare();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        MijlocFix clona = (MijlocFix) super.clone();
        if(dataAchizitie!=null){
            clona.setDataAchizitie((Date)dataAchizitie.clone());

        }
        if(locatie!=null){
            clona.setLocatie((Locatie)locatie.clone());
        }
        return clona;
    }
}

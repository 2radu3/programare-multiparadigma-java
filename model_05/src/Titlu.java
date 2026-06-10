public class Titlu {
    String simbol;
    String denumire;
    double deschidere;
    double max;
    double min;
    double inchidere;
    long volum;

    public Titlu(String simbol, String denumire, double deschidere, double max, double min, double inchidere, long volum) {
        this.simbol = simbol;
        this.denumire = denumire;
        this.deschidere = deschidere;
        this.max = max;
        this.min = min;
        this.inchidere = inchidere;
        this.volum = volum;
    }

    public Titlu() {
    }
}

public class Achizitie {
    String cod;
    int an;
    int luna;
    int zi;
    int cantitate;
    double pret;

    public Achizitie(String cod, int an, int luna, int zi, int cantitate, double pret) {
        this.cod = cod;
        this.an = an;
        this.luna = luna;
        this.zi = zi;
        this.cantitate = cantitate;
        this.pret = pret;
    }

    public Achizitie() {
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public int getLuna() {
        return luna;
    }

    public void setLuna(int luna) {
        this.luna = luna;
    }

    public int getZi() {
        return zi;
    }

    public void setZi(int zi) {
        this.zi = zi;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    @Override
    public String toString() {
        return "Achizitie{" +
                "cod='" + cod + '\'' +
                ", an=" + an +
                ", luna=" + luna +
                ", zi=" + zi +
                ", cantitate=" + cantitate +
                ", pret=" + pret +
                '}';
    }

    public double valoare() {
        return this.cantitate * this.pret;
    }
}

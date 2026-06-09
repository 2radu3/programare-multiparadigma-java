public class Apartament {
    int NumarApartament;
    int Suprafata;
    int NumarPersoane;

    public Apartament(int numarApartament, int suprafata, int numarPersoane) {
        NumarApartament = numarApartament;
        Suprafata = suprafata;
        NumarPersoane = numarPersoane;
    }

    @Override
    public String toString() {
        return "Apartament{" +
                "NumarApartament=" + NumarApartament +
                ", Suprafata=" + Suprafata +
                ", NumarPersoane=" + NumarPersoane +
                '}';
    }
}

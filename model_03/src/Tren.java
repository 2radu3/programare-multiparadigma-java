import java.util.List;

public class Tren {
    int codTren;
    String tipTren;
    List<Vagon> vagoane;

    public Tren(int codTren, String tipTren, List<Vagon> vagoane) {
        this.codTren = codTren;
        this.tipTren = tipTren;
        this.vagoane = vagoane;
    }

    public Tren() {
    }
}

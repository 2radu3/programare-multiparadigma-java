package seminar.seminar2.g1061;

public enum Categorie {
    TERENURI(211), CONSTRUCTII(212),
    ECHIPAMENTE_UTILAJE(2131), MIJLOACE_TRANSPORT (2133), MOBILIER (214);

    private int simbolContabil;

    Categorie(int simbolContabil) {
        this.simbolContabil = simbolContabil;
    }

    public int getSimbolContabil() {
        return simbolContabil;
    }

    public void setSimbolContabil(int simbolContabil) {
        this.simbolContabil = simbolContabil;
    }
}

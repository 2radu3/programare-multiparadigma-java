public class Pacient {
    long cnp;
    String numePacient;
    int varstaPacient;
    int CodSectie;

    public Pacient() {
    }

    public Pacient(long cnp, String numePacient, int varstaPacient, int codSectie) {
        this.cnp = cnp;
        this.numePacient = numePacient;
        this.varstaPacient = varstaPacient;
        CodSectie = codSectie;
    }
}

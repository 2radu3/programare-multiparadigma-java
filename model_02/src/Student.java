public class Student {
    int IdStundet;
    String nume;
    String prenume;

    public Student(int idStundet, String nume, String prenume) {
        IdStundet = idStundet;
        this.nume = nume;
        this.prenume = prenume;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "IdStundet=" + IdStundet +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                '}';
    }
}

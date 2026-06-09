import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


public class Main {
    public static List<Student> studenti = new ArrayList<>();

    public static void citireDate() throws Exception{
        FileReader fr = new FileReader("S11_studenti.json");
        JSONArray arr = new JSONArray(new JSONTokener(fr));

        for(int i=0; i< arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Student s = new Student();

            s.IdStundet = obj.getInt("IdStudent");
            s.nume = obj.getString("Nume");
            s.prenume = obj.getString("Prenume");

            studenti.add(s);
        }
        fr.close();

        int contor = 0;
        for(Student st : studenti){
            System.out.println(st.toString());
            contor++;
        }
        System.out.println("Numar total de studenti: " + contor);
    }

    public static void citireDinFisier() throws Exception {
        Map<Integer, List<Double>> noteStudenti = new HashMap<>();
        Map<String, Integer> note = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader("S11_note.txt"));
        String linie;
        while((linie = br.readLine()) != null) {
            System.out.println(linie);
            linie = linie.trim();

            String[] parti = linie.split(",");
            int idStudent = Integer.parseInt(parti[0].trim());
            String materie = parti[1].trim();
            double nota = Double.parseDouble(parti[2].trim());

            if(!noteStudenti.containsKey(idStudent)) {
                List<Double> listaNoua = new ArrayList<>();
                listaNoua.add(nota);
                noteStudenti.put(idStudent, listaNoua);
            } else {
                List<Double> lista = noteStudenti.get(idStudent);
                lista.add(nota);
            }

            if(note.containsKey(materie)){
                note.put(materie, note.get(materie) + 1);
            } else {
                note.put(materie, 1);
            }
        }

        for(Map.Entry<Integer, List<Double>> entry : noteStudenti.entrySet()) {
            int id = entry.getKey();
            List<Double> note2 = entry.getValue();

            int numarNote = note2.size();
            double suma = 0;
            for(double n : note2) {
                suma += n;
            }
            double media = suma / numarNote;
            System.out.println(id + "," + numarNote + "," + media);
        }
        br.close();

        System.out.println("2) - Lista discipline: ");
        for(Map.Entry<String, Integer> entry : note.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue() + " note");
        }
    }

    public static void cautaMaterie() throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti denumirea materiei: ");
        String materie = scanner.nextLine().trim();

        Map<Integer, Double> noteMaterie = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader("S11_note.txt"));
        String linie;
        while((linie = br.readLine()) != null) {
            linie = linie.trim();

            String[] parti = linie.split(",");
            int idStudent = Integer.parseInt(parti[0].trim());
            String mat = parti[1].trim();
            double nota = Double.parseDouble(parti[2].trim());

            if(mat.equals(materie)) {
                noteMaterie.put(idStudent, nota);
            }
        }
        br.close();

        for(Student st : studenti) {
            if(noteMaterie.containsKey(st.IdStundet)) {
                double nota = noteMaterie.get(st.IdStundet);
                System.out.println(st.nume + ", " + st.prenume + ", " + nota);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        citireDate();
        citireDinFisier();
        cautaMaterie();
    }
}
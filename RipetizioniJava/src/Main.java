import dao.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DAO.registerDriver();
        ArrayList<Persona> persone = DAO.getUtenti();
        for (Persona p: persone)
            System.out.println(p);

        DAO.aggiungiCorso("Matematica", "Corso di matematica");
        DAO.aggiungiDocente("Mario", "Rossi");
        DAO.assocDocenteCorso("Mario", "Rossi", "Matematica");

        DAO.getRipetizioni();

        DAO.aggiungiPrenotazione("Matematica", "Mario", "Rossi", "2022-03-10", 16);
        DAO.rimuoviPrenotazione(1);

        ArrayList<Corso> corsi = DAO.getCorsi();
        for (Corso c: corsi)
            System.out.println(c);
        System.out.println("FINE");
    }
}

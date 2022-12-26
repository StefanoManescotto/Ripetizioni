package com.example.ripetizioni0.DAO;

import java.sql.*;
import java.util.ArrayList;

//TODO: remove unused methods

public class DAO {

    private final String url1;
    private final String user;
    private final String password;


    public DAO(String url1, String user, String password){
        this.url1 = url1;
        this.user = user;
        this.password = password;

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver correttamente registrato");
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    private Connection startConnection() throws SQLException{
        Connection conn;
        conn = DriverManager.getConnection(url1, user, password);
        return conn;
    }

    public void aggiungiCorso(String titolo, String descrizione){
        Connection conn1 = null;

        try {
            conn1 = startConnection();

            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO CORSI (TITOLO, DESCRIZIONE) VALUES ( \"" + titolo + "\",\""+ descrizione +"\");");
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn1);
        }
    }

    public boolean aggiungiDocente(String nome, String cognome){
        Connection conn1 = null;

        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();
            ResultSet rs;
            rs = st.executeQuery("SELECT * FROM DOCENTI WHERE NOME = \"" + nome + "\" AND COGNOME = \"" + cognome + "\"");
            if(!rs.next()) {
                st.executeUpdate("INSERT INTO DOCENTI (NOME, COGNOME) VALUES ( \"" + nome + "\",\"" + cognome + "\");");
            }else{
                return false;
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn1);
        }
        return true;
    }

    public void assocDocenteCorso(String nome, String cognome, String corso){
        Connection conn1 = null;

        try {
            conn1 = startConnection();

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM DOCENTI");
            while(rs.next()){
                if(rs.getString("NOME").compareTo(nome) == 0
                        && rs.getString("COGNOME").compareTo(cognome) == 0){
                    st.executeUpdate("INSERT INTO CORSO_DOCENTE (IDCORSO, IDDOCENTE) VALUES (\"" + corso +
                            "\",\"" + rs.getInt("IDDOCENTE") + "\");");
                    break;
                }
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn1);
        }
    }

    public void getRipetizioni(){
        Connection conn1 = null;

        try {
            conn1 = startConnection();

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CORSO_DOCENTE, DOCENTI " +
                    "WHERE CORSO_DOCENTE.IDDOCENTE = DOCENTI.IDDOCENTE ORDER BY TITOLO");
            while(rs.next()){
                System.out.println(rs.getString("TITOLO") + ": " + rs.getString("NOME") +
                        " " + rs.getString("COGNOME"));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn1);
        }
    }

    public ArrayList<Corso> getCorsi() {
        Connection conn1 = null;
        ArrayList<Corso> out = new ArrayList<>();
        try {
            conn1 = startConnection();

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CORSI");
            while (rs.next()) {
                Corso p = new Corso(rs.getString("TITOLO"), rs.getString("DESCRIZIONE"));
                out.add(p);
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn1);
        }
        return out;
    }

    public ArrayList<Persona> getUtenti() {
        Connection conn1 = null;
        ArrayList<Persona> out = new ArrayList<>();
        try {
            conn1 = startConnection();

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM UTENTI");
            while (rs.next()) {
                Persona p = new Persona(rs.getInt("IDUTENTE"), rs.getString("NOME"),rs.getString("COGNOME"), rs.getString("EMAIL"), rs.getString("ruolo"));
                out.add(p);
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeConnection(conn1);
        }
        return out;
    }

    public Persona getUtente(String email) {
        Connection conn1 = null;
        Persona out = null;

        try {
            conn1 = startConnection();

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM UTENTI WHERE EMAIL = \"" + email + "\"");
            if(rs.next()) {
                out = new Persona(rs.getInt("IDUTENTE"), rs.getString("NOME"),rs.getString("COGNOME"), rs.getString("EMAIL"),
                        rs.getString("RUOLO"), rs.getString("PASSWORD"));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeConnection(conn1);
        }
        return out;
    }


    public ArrayList<Prenotazione> getPrenotazioni(){
        Connection conn1 = null;
        ArrayList<Prenotazione> out = new ArrayList<>();
        try {
            conn1 = startConnection();

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PRENOTAZIONI, DOCENTI WHERE DOCENTI.IDDOCENTE = PRENOTAZIONI.IDDOCENTE ");
            while (rs.next()) {
                out.add(new Prenotazione(rs.getInt("IDPRENOTAZIONE"), rs.getString("IDCORSO"), rs.getString("NOME"), rs.getString("COGNOME"),
                        rs.getInt("IDUTENTE"), rs.getInt("ORAPREN"), rs.getString("DATAPREN"), rs.getString("stato")));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeConnection(conn1);
        }
        return out;
    }

    public void aggiungiPrenotazione(int idUtente, String corso, String nomeDocente, String cognomeDocente, String data, int ora){
        Connection conn1 = null;
        int idDocente = 0;

        if(ora < 15 || ora > 19){
            return;
        }

        try {
            conn1 = startConnection();

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM DOCENTI");

            while(rs.next()){
                if(rs.getString("NOME").compareTo(nomeDocente) == 0
                        && rs.getString("COGNOME").compareTo(cognomeDocente) == 0){
                    idDocente = rs.getInt("IDDOCENTE");
                    break;
                }
            }

            rs = st.executeQuery("SELECT * FROM PRENOTAZIONI WHERE IDDOCENTE = \"" + idDocente+ "\"");

            while(rs.next()){
                if(rs.getString("DATAPREN").compareTo(data) == 0
                        && rs.getInt("ORAPREN") == ora && !rs.getString("STATO").equals("cancellata")){
                    System.out.println("Data e orario occupati");
                    return;
                }
            }
            rs.close();

            st.executeUpdate("INSERT INTO PRENOTAZIONI (IDUTENTE, IDCORSO, IDDOCENTE, DATAPREN, ORAPREN) VALUES (\"" + idUtente + "\"," +
                    "\"" + corso + "\"," +
                    "\"" + idDocente +
                    "\",\"" + data + "\"" +
                    ",\"" + ora + "\");");

            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn1);
        }
    }

    public ArrayList<Prenotazione> getPrenotazioniUtente(int idUtente){
        Connection conn1 = null;
        ArrayList<Prenotazione> prenotazioni = new ArrayList<>();
        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PRENOTAZIONI, DOCENTI WHERE IDUTENTE = \"" + idUtente + "\" AND DOCENTI.IDDOCENTE = PRENOTAZIONI.IDDOCENTE ORDER BY DATAPREN");
            while(rs.next()){
                prenotazioni.add(new Prenotazione(rs.getInt("IDPRENOTAZIONE"), rs.getString("IDCORSO"), rs.getString("NOME"), rs.getString("COGNOME"),
                        idUtente, rs.getInt("ORAPREN"), rs.getString("DATAPREN"), rs.getString("stato")));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn1);
        }
        return prenotazioni;
    }

    public void rimuoviPrenotazione(int idPrenotazione){
        Connection conn1 = null;

        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();

            st.executeUpdate("DELETE FROM PRENOTAZIONI WHERE IDPRENOTAZIONE = " + idPrenotazione);
            st.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn1);
        }
    }

    public ArrayList<Docente> getDocentiMateria(String materia){
        Connection conn1 = null;
        ArrayList<Docente> docenti = new ArrayList<>();

        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();

            String query = "SELECT DOCENTI.IDDOCENTE, DOCENTI.NOME, DOCENTI.COGNOME FROM DOCENTI, CORSO_DOCENTE WHERE CORSO_DOCENTE.IDCORSO =  \"" + materia + "\" " +
                    "AND CORSO_DOCENTE.IDDOCENTE = DOCENTI.IDDOCENTE";
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                docenti.add(new Docente(rs.getInt("IDDOCENTE"), rs.getString("NOME"), rs.getString("COGNOME")));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn1);
        }
        return docenti;
    }

    public ArrayList<Docente> getAltriDocenti(String materia){
        Connection conn1 = null;
        ArrayList<Docente> docenti = new ArrayList<>();

        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();

            String query = "SELECT DOCENTI.IDDOCENTE, DOCENTI.NOME, DOCENTI.COGNOME FROM DOCENTI WHERE IDDOCENTE NOT IN (" +
                    "SELECT IDDOCENTE FROM CORSO_DOCENTE WHERE IDCORSO = \"" + materia + "\")";
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                docenti.add(new Docente(rs.getInt("IDDOCENTE"), rs.getString("NOME"), rs.getString("COGNOME")));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn1);
        }
        return docenti;
    }

    public boolean addMateria(String materia, String descrizione){
        Connection conn1 = null;

        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();

            ResultSet rs;
            rs = st.executeQuery("SELECT * FROM CORSI WHERE TITOLO = \"" + materia + "\"");

            if(!rs.next()){
                st.executeUpdate("INSERT INTO CORSI (TITOLO, DESCRIZIONE) VALUES ( \"" + materia + "\",\""+ descrizione +"\");");
            }else{
                return false;
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn1);
        }
        return true;
    }

    public void removeMateria(String materia){
        Connection conn1 = null;

        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM CORSI WHERE TITOLO = \"" + materia + "\"");
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn1);
        }
    }

    public void removeDocente(int idDocente){
        Connection conn1 = null;

        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM DOCENTI WHERE IDDOCENTE = \"" + idDocente + "\"");
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn1);
        }
    }

    public ArrayList<Docente> getOrariDocenti(String materia, String from, String to){
        Connection conn1 = null;
        ArrayList<Docente> docenti = getDocentiMateria(materia);
        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();

            ResultSet rs;
            for(Docente s : docenti){
                rs = st.executeQuery("SELECT DATAPREN, ORAPREN FROM PRENOTAZIONI WHERE IDDOCENTE = \"" + s.getIdDocente() + "\" AND STATO != \"cancellata\"");
                while(rs.next()){
                    if(isBetweenDate(rs.getString("DATAPREN"), from, to)) {
                        s.addBusy(rs.getString("DATAPREN"), rs.getInt("ORAPREN"));
                    }
                }
                rs.close();
            }
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn1);
        }

        return docenti;
    }

    public ArrayList<Docente> getAllDocenti(){
        Connection conn1 = null;
        ArrayList<Docente> docenti = new ArrayList<>();
        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();

            ResultSet rs;
            rs = st.executeQuery("SELECT * FROM DOCENTI");
            while(rs.next()){
                docenti.add(new Docente(rs.getInt("IDDOCENTE"), rs.getString("NOME"), rs.getString("COGNOME")));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn1);
        }

        return docenti;
    }

    public boolean cambiaPrenStato(int idPrenotazione, String newState, int idUtente, String ruolo){
        Connection conn1 = null;

        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT IDUTENTE FROM PRENOTAZIONI WHERE IDPRENOTAZIONE = \"" + idPrenotazione + "\"");
            while(rs.next()){
                if(rs.getInt("IDUTENTE") != idUtente && !ruolo.equals("amministratore")){
                    st.close();
                    rs.close();
                    return false;
                }
            }


            st.executeUpdate("UPDATE PRENOTAZIONI SET STATO = \"" + newState + "\" WHERE IDPRENOTAZIONE = \"" + idPrenotazione + "\"");

            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn1);
        }
        return true;
    }

    private boolean isBetweenDate(String date, String from, String to){
        return isDateGreater(date, from) && isDateGreater(to, date);
    }

    private boolean isDateGreater(String date1, String date2){
        String[] d1 = date1.split("-");
        String[] d2 = date2.split("-");

        if(Integer.parseInt(d1[0]) > Integer.parseInt(d2[0])){
            return true;
        }else if(Integer.parseInt(d1[0]) == Integer.parseInt(d2[0])){
            if(Integer.parseInt(d1[1]) > Integer.parseInt(d2[1])){
                return true;
            }else if(Integer.parseInt(d1[1]) == Integer.parseInt(d2[1])){
                return Integer.parseInt(d1[2]) >= Integer.parseInt(d2[2]);
            }
        }
        return false;
    }

    private boolean closeConnection(Connection conn){
        if (conn != null) {
            try {
                conn.close();
                return true;
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
        }
        return false;
    }
}

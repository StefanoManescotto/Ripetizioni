package com.example.ripetizioni0.DAO;

import java.sql.*;
import java.util.ArrayList;

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

    public boolean aggiungiDocente(String nome, String cognome){
        Connection conn1 = null;

        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();
            ResultSet rs;
            rs = st.executeQuery("SELECT * FROM DOCENTI WHERE NOME = \"" + nome + "\" AND COGNOME = \"" + cognome + "\"");
            if(!rs.next()) {
                st.executeUpdate("INSERT INTO DOCENTI (NOME, COGNOME) VALUES ( \"" + nome + "\",\"" + cognome + "\");");
            }else if(!rs.getBoolean("ATTIVA")){
                st.executeUpdate("UPDATE DOCENTI SET ATTIVA = 1 WHERE NOME = \"" + nome + "\" AND COGNOME = \"" + cognome + "\"");
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

    public boolean aggiungiUtente(String nome, String cognome, String email, String psw, String ruolo){
        Connection conn1 = null;

        try {
            conn1 = startConnection();

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM UTENTI WHERE EMAIL = \"" + email + "\"");
            if(rs.next()){
                return false;
            }

            st.executeUpdate("INSERT INTO UTENTI (NOME, COGNOME, EMAIL, PASSWORD, RUOLO) VALUES (\"" + nome +"\",\"" + cognome + "\", \""
                    + email +"\", \"" + psw + "\", \"" + ruolo + "\");");

            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn1);
        }
        return true;
    }

    public boolean addAssocDocenteCorso(String nome, String cognome, String corso){
        Connection conn1 = null;
        int idDocente;

        try {
            conn1 = startConnection();

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM DOCENTI WHERE ATTIVA = 1 AND NOME = \"" + nome +"\" AND COGNOME = \"" + cognome +"\" ");
            if(rs.next()){
                idDocente = rs.getInt("IDDOCENTE");
                rs.close();
                rs = st.executeQuery("SELECT * FROM CORSO_DOCENTE WHERE IDCORSO = \"" + corso + "\" AND IDDOCENTE = \"" + idDocente + "\"");
                if(rs.next()){
                    return false;
                }else{
                    st.executeUpdate("INSERT INTO CORSO_DOCENTE (IDCORSO, IDDOCENTE) VALUES (\"" + corso + "\",\"" + idDocente + "\");");
                }
            }else{
                return false;
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn1);
        }
        return true;
    }

    public boolean removeAssocDocenteCorso(String nome, String cognome, String corso){
        Connection conn1 = null;
        int idDocente;

        try {
            conn1 = startConnection();

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM DOCENTI WHERE ATTIVA = 1 AND NOME = \"" + nome +"\" AND COGNOME = \"" + cognome +"\" ");
            if(rs.next()){
                idDocente = rs.getInt("IDDOCENTE");
                rs.close();
                rs = st.executeQuery("SELECT * FROM CORSO_DOCENTE WHERE IDCORSO = \"" + corso + "\" AND IDDOCENTE = \"" + idDocente + "\"");
                if(rs.next()){
                    st.executeUpdate("DELETE FROM CORSO_DOCENTE WHERE IDCORSO = \"" + corso + "\" AND IDDOCENTE = \"" + idDocente + "\"");
                    st.executeUpdate("UPDATE PRENOTAZIONI SET STATO = \"cancellata\" WHERE IDCORSO = \"" + corso + "\" AND IDDOCENTE = \"" + idDocente + "\"");
                }else{
                     return false;
                }
            }else{
                return false;
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn1);
        }
        return true;
    }

    public ArrayList<Corso> getCorsi() {
        Connection conn1 = null;
        ArrayList<Corso> out = new ArrayList<>();
        try {
            conn1 = startConnection();

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CORSI WHERE ATTIVA = TRUE");
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
            ResultSet rs = st.executeQuery("SELECT * FROM PRENOTAZIONI, DOCENTI WHERE DOCENTI.IDDOCENTE = PRENOTAZIONI.IDDOCENTE ORDER BY DATAPREN DESC, ORAPREN");
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

    public void aggiungiPrenotazione(int idUtente, String corso, int idDocente, String data, int ora){
        Connection conn1 = null;

        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();

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

    public boolean aggiungiPrenotazioni(int idUtente, String corso, String nomeDocente, String cognomeDocente, String data, int[] ore){
        Connection conn1 = null;
        int idDocente;

        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM DOCENTI WHERE ATTIVA = 1 AND NOME = \"" + nomeDocente +"\" AND COGNOME = \"" + cognomeDocente +"\" ");

            if(rs.next()){
                idDocente = rs.getInt("IDDOCENTE");
            }else{
                return false;
            }
            rs.close();

            String query = "SELECT * FROM PRENOTAZIONI WHERE (IDUTENTE = \"" + idUtente + "\" OR IDDOCENTE = \"" + idDocente + "\")" +
                    "AND DATAPREN = \"" + data + "\" AND STATO != \"cancellata\"" +
                    "AND (ORAPREN = \"" + ore[0] + "\"";
            for(int o : ore){
                if(o < 15 || o > 19){
                    return false;
                }
                query += "OR ORAPREN = \"" + o + "\"";
            }
            query += ")";

            rs = st.executeQuery(query);
            if(rs.next()){
                rs.close();
                return false;
            }
            rs.close();

            rs = st.executeQuery("SELECT * FROM CORSI WHERE TITOLO = \"" + corso + "\"");
            if(!rs.next() || !rs.getBoolean("ATTIVA")){
                rs.close();
                st.close();
                return false;
            }

            rs.close();
            st.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn1);
        }

        for(int o : ore){
            aggiungiPrenotazione(idUtente, corso, idDocente, data, o);
        }
        return true;
    }

    public ArrayList<Prenotazione> getPrenotazioniUtente(int idUtente){
        Connection conn1 = null;
        ArrayList<Prenotazione> prenotazioni = new ArrayList<>();
        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PRENOTAZIONI, DOCENTI WHERE IDUTENTE = \"" + idUtente + "\" AND DOCENTI.IDDOCENTE = PRENOTAZIONI.IDDOCENTE " +
                    "ORDER BY DATAPREN DESC, ORAPREN");
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

    public ArrayList<Docente> getDocentiMateria(String materia){
        Connection conn1 = null;
        ArrayList<Docente> docenti = new ArrayList<>();

        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();

            String query = "SELECT DOCENTI.IDDOCENTE, DOCENTI.NOME, DOCENTI.COGNOME FROM DOCENTI, CORSO_DOCENTE WHERE CORSO_DOCENTE.IDCORSO =  \"" + materia + "\" " +
                    "AND CORSO_DOCENTE.IDDOCENTE = DOCENTI.IDDOCENTE AND ATTIVA = 1";
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
                    "SELECT IDDOCENTE FROM CORSO_DOCENTE WHERE IDCORSO = \"" + materia + "\") and ATTIVA = 1";
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
            }else if(!rs.getBoolean("ATTIVA")){
                st.executeUpdate("UPDATE CORSI SET ATTIVA = 1, DESCRIZIONE = \"" + descrizione + "\" WHERE TITOLO = \"" + materia + "\"");
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

    public void rimuoviMateria(String materia){
        Connection conn1 = null;

        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE CORSI SET ATTIVA = 0 WHERE TITOLO = \"" + materia + "\"");
            st.executeUpdate("DELETE FROM CORSO_DOCENTE WHERE IDCORSO = \"" + materia + "\"");
            cancellaAllPrenByMateria();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn1);
        }
    }

    public void rimuoviDocente(int idDocente){
        Connection conn1 = null;

        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE DOCENTI SET ATTIVA = 0 WHERE IDDOCENTE = \"" + idDocente + "\"");
            st.executeUpdate("DELETE FROM CORSO_DOCENTE WHERE IDDOCENTE = \"" + idDocente + "\"");
            cancellaAllPrenByDocente();
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
            rs = st.executeQuery("SELECT * FROM DOCENTI WHERE ATTIVA = 1");
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

    public void cancellaAllPrenByMateria(){
        Connection conn1 = null;
        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE PRENOTAZIONI SET STATO = \"" + "cancellata" + "\" WHERE IDCORSO in (" +
                    "SELECT TITOLO FROM CORSI WHERE ATTIVA = 0)");
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn1);
        }
    }

    public void cancellaAllPrenByDocente(){
        Connection conn1 = null;
        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE PRENOTAZIONI SET STATO = \"" + "cancellata" + "\" WHERE IDDOCENTE in (" +
                    "SELECT IDDOCENTE FROM DOCENTI WHERE ATTIVA = 0)");
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn1);
        }
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
            return false;
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

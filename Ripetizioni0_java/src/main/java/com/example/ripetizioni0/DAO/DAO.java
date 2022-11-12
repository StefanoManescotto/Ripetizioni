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


    public void aggiungiCorso(String titolo, String descrizione){
        Connection conn1 = null;

        try {
            conn1 = startConnection();

            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO CORSI (TITOLO, DESCRIZIONE) VALUES ( \"" + titolo + "\",\""+ descrizione +"\");");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn1);
        }
    }

    public void aggiungiDocente(String nome, String cognome){
        Connection conn1 = null;

        try {
            conn1 = startConnection();

            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO DOCENTI (NOME, COGNOME) VALUES ( \"" + nome + "\",\""+ cognome +"\");");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn1);
        }
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
                    st.executeUpdate("INSERT INTO CORSO_DOCENTE (TITOLO, IDDOCENTE) VALUES (\"" + corso +
                            "\",\"" + rs.getInt("IDDOCENTE") + "\");");
                    break;
                }
            }
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
                Persona p = new Persona(rs.getString("NOME"),rs.getString("COGNOME"), rs.getString("EMAIL"), rs.getString("ruolo"));
                out.add(p);
            }
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
        System.out.println("EMAIL: " + email);
        try {
            conn1 = startConnection();

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM UTENTI WHERE EMAIL = \"" + email + "\"");
            if(rs.next()) {
                System.out.println("SONO QUI: " + email);
                out = new Persona(rs.getString("NOME"),rs.getString("COGNOME"), rs.getString("EMAIL"),
                        rs.getString("RUOLO"), rs.getString("PASSWORD"));
            }
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
            ResultSet rs = st.executeQuery("SELECT * FROM PRENOTAZIONI");
            while (rs.next()) {
                Prenotazione p = new Prenotazione(rs.getString("IDCORSO"), rs.getInt("IDDOCENTE"),
                        rs.getInt("IDUTENTE"), rs.getInt("ORAPREN"), rs.getString("DATAPREN"));
                out.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeConnection(conn1);
        }
        return out;
    }

    public void aggiungiPrenotazione(String corso, String nomeDocente, String cognomeDocente, String data, int ora){
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

            rs = st.executeQuery("SELECT * FROM PRENOTAZIONI");

            while(rs.next()){
                if(rs.getString("DATAPREN").compareTo(data) == 0
                        && rs.getInt("ORAPREN") == ora){
                    System.out.println("Data e orario occupati");
                    return;
                }
            }

            //TODO: change value 1 for idUtente
            st.executeUpdate("INSERT INTO PRENOTAZIONI (IDUTENTE, IDCORSO, IDDOCENTE, DATAPREN, ORAPREN) VALUES (1, " +
                    "\"" + corso + "\"," +
                    "\"" + idDocente +
                    "\",\"" + data + "\"" +
                    ",\"" + ora + "\");");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn1);
        }
    }

    public void rimuoviPrenotazione(int idPrenotazione){
        Connection conn1 = null;

        try {
            conn1 = startConnection();
            Statement st = conn1.createStatement();

            st.executeUpdate("DELETE FROM PRENOTAZIONI WHERE IDPRENOTAZIONE = " + idPrenotazione);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn1);
        }
    }
}

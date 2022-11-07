package dao;

import java.sql.*;
import java.util.ArrayList;

public class DAO {

    private static final String url1 = "jdbc:mysql://localhost:3306/ripetizioni";
    private static final String user = "root";
    private static final String password = "";

    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver correttamente registrato");
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }


    private static Connection startConnection() throws SQLException{
        Connection conn = null;
        conn = DriverManager.getConnection(url1, user, password);
        if (conn != null) {
            //System.out.println("Connected to the database test");
        }

        return conn;
    }

    private static boolean closeConnection(Connection conn){
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


    public static void aggiungiCorso(String titolo, String descrizione){
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

    public static void aggiungiDocente(String nome, String cognome){
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

    public static void assocDocenteCorso(String nome, String cognome, String corso){
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

    public static void getRipetizioni(){
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

    public static ArrayList<Corso> getCorsi() {
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

    public static ArrayList<Persona> getUtenti() {
        Connection conn1 = null;
        ArrayList<Persona> out = new ArrayList<>();
        try {
            conn1 = startConnection();

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM UTENTI");
            while (rs.next()) {
                Persona p = new Persona(rs.getString("NOME"),rs.getString("COGNOME"), rs.getString("EMAIL"));
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

    public static void aggiungiPrenotazione(String corso, String nDocente, String cDocente, String data, int ora){
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
                if(rs.getString("NOME").compareTo(nDocente) == 0
                        && rs.getString("COGNOME").compareTo(cDocente) == 0){
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

    public static void rimuoviPrenotazione(int idPrenotazione){
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

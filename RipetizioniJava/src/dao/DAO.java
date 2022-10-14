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

    public static ArrayList<Persona> queryDB() {
        Connection conn1 = null;
        ArrayList<Persona> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

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
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        return out;
    }

    public static void aggiungiCorso(String titolo, String descrizione){
        Connection conn1 = null;

        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO CORSI (TITOLO, DESCRIZIONE) VALUES ( \"" + titolo + "\",\""+ descrizione +"\");");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
    }

    public static ArrayList<Corso> getCorsi() {
        Connection conn1 = null;
        ArrayList<Corso> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CORSI");
            while (rs.next()) {
                Corso p = new Corso(rs.getString("TITOLO"), rs.getString("DESCRIZIONE"));
                out.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        return out;
    }

}

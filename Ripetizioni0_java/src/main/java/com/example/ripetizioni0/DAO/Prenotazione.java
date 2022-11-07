package com.example.ripetizioni0.DAO;

public class Prenotazione {
    private String corso, data;
    private int idDocente, idUtente, ora;

    public Prenotazione(String corso, int idDocente, int idUtente, int ora, String data) {
        this.corso = corso;
        this.idDocente = idDocente;
        this.idUtente = idUtente;
        this.ora = ora;
        this.data = data;
    }

    public String getCorso() {
        return corso;
    }

    public String getData() {
        return data;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public int getOra() {
        return ora;
    }
}

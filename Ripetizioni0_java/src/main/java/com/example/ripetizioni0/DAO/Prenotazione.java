package com.example.ripetizioni0.DAO;

public class Prenotazione {

    private String corso, data, stato;
    private String nomeDocente, cognomeDocente;
    private int idPrenotazione, idDocente, idUtente, ora;

    public Prenotazione(int idPrenotazione, String corso, int idDocente, int idUtente, int ora, String data, String stato) {
        this.idPrenotazione = idPrenotazione;
        this.corso = corso;
        this.idDocente = idDocente;
        this.idUtente = idUtente;
        this.ora = ora;
        this.data = data;
        this.stato = stato;
    }

    public Prenotazione(int idPrenotazione, String corso, String nomeDocente, String cognomeDocente, int idUtente, int ora, String data, String stato) {
        this.idPrenotazione = idPrenotazione;
        this.corso = corso;
        this.nomeDocente = nomeDocente;
        this.cognomeDocente = cognomeDocente;
        this.idUtente = idUtente;
        this.ora = ora;
        this.data = data;
        this.stato = stato;
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

    public int getYear(){
        return Integer.valueOf(data.split("-")[0]);
    }

    public int getMonth(){
        return Integer.valueOf(data.split("-")[1]) - 1;
    }

    public int getDay(){
        return Integer.valueOf(data.split("-")[2]);
    }

    public String getNomeDocente() {
        return nomeDocente;
    }

    public String getStato() {
        return stato;
    }

    //    public void setNomeDocente(String nomeDocente) {
//        this.nomeDocente = nomeDocente;
//    }

    public String getCognomeDocente() {
        return cognomeDocente;
    }

//    public void setCognomeDocente(String cognomeDocente) {
//        this.cognomeDocente = cognomeDocente;
//    }

    @Override
    public String toString() {
        return corso + " " + idDocente + " " + data  + " " + ora;
    }
}

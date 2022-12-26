package com.example.ripetizioni0.DAO;

import java.util.ArrayList;

public class Corso {
    private String titolo, descrizione;
    private ArrayList<String> insegnanti = new ArrayList<>();

    public Corso(String titolo, String descrizione) {
        this.titolo = titolo;
        this.descrizione = descrizione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void addInsegnante(String i){
        insegnanti.add(i);
    }

    @Override
    public String toString() {
        return titolo + " " + descrizione;
    }
}

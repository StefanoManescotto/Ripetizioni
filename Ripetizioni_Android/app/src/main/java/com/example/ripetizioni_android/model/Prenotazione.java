package com.example.ripetizioni_android.model;

public class Prenotazione {
    public String ora;
    public Prenotazione(int ora){
        this.ora = String.valueOf(ora);
    }

    @Override
    public String toString() {
        return "ora = " + ora;
    }
}

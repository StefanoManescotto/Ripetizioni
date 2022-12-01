package com.example.ripetizioni_android.model;

public class Prenotazione {
    public String ora;
    private State currentState;
    public enum State {
        ATTIVA,
        EFFETTUATA,
        DISDETTA
    }

    public Prenotazione(int ora){
        this.ora = String.valueOf(ora);
        currentState = State.ATTIVA;
    }

    public void setStato(State state) {
        currentState = state;
    }

    public String getStato() {
        return currentState.toString();
    }

    @Override
    public String toString() {
        return "ora = " + ora;
    }
}

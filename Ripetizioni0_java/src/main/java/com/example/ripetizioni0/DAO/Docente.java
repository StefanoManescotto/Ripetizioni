package com.example.ripetizioni0.DAO;



import java.util.ArrayList;

public class Docente {
    private int idDocente;
    private String nome, cognome;
    private ArrayList<PairDayTime> busyDays = new ArrayList<>();
    private ArrayList<String> materie = new ArrayList<>();

    public Docente(int idDocente, String nome, String cognome) {
        setIdDocente(idDocente);
        this.nome = nome;
        this.cognome = cognome;
    }

    public void addBusy(String day, int time){
        busyDays.add(new PairDayTime(day, time));
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        if(idDocente < 0){
            throw new IllegalArgumentException();
        }
        this.idDocente = idDocente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nomeDocente) {
        this.nome = nomeDocente;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public ArrayList<PairDayTime> getBusyDays() {
        return busyDays;
    }

    public void setBusyDays(ArrayList<PairDayTime> busyDays) {
        this.busyDays = busyDays;
    }

    public ArrayList<String> getMaterie() {
        return materie;
    }

    public void setMaterie(ArrayList<String> materie) {
        this.materie = materie;
    }

    public void addMateria(String materia){
        materie.add(materia);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() == this.getClass()){
            return nome.equals(((Docente) obj).nome) && cognome.equals(((Docente) obj).cognome);
        }
        return false;
    }

    @Override
    public String toString() {
        return nome + " " + cognome;
    }
}

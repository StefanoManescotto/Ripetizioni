package com.example.ripetizioni0.DAO;

public class Persona {
    private String nome;
    private String cognome;
    private String email;


    public Persona(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }


    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail(){
        return email;
    }

    @Override
    public String toString() {
        return nome + " " + cognome + " " + email;
    }
}

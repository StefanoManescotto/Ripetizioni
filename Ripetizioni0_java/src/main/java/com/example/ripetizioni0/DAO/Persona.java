package com.example.ripetizioni0.DAO;

public class Persona {
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String ruolo;


    public Persona(int id, String nome, String cognome, String email, String ruolo) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.ruolo = ruolo;
    }

    public Persona(int id, String nome, String cognome, String email, String ruolo, String password) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.ruolo = ruolo;
        this.password = password;
    }

//    public Persona(String nome, String cognome, String email, String ruolo, String password) {
//        this.nome = nome;
//        this.cognome = cognome;
//        this.email = email;
//        this.ruolo = ruolo;
//        this.password = password;
//    }


    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getRuolo(){
        return ruolo;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return nome + " " + cognome + " " + email + " " + ruolo;
    }
}

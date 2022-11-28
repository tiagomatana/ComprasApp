package com.edina.compras.model;

import java.io.Serializable;

public class User implements Serializable {
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public User(){
        this.nome = "";
        this.email = "";
    }

    private int id;
    private String nome;
    private String email;
    private String password;

    public boolean validate() {
        return !nome.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }

    public boolean isLogged() {
        return !nome.isEmpty() && !email.isEmpty();
    }
}

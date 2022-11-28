package com.edina.compras.model;

import java.io.Serializable;

public class Item implements Serializable {

    public Item(int id, String descricao, int quantidade, String email, int status) {
        this.id = id;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.email = email;
        this.status = status;
    }

    public Item() {
        this.quantidade = 0;
        this.status = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean getStatus() {
        return Boolean.parseBoolean(String.valueOf(status == 1 ? Boolean.TRUE : Boolean.FALSE));
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    private int id;
    private String email;
    private String descricao;
    private int quantidade;
    private int status;




}

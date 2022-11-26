package com.edina.compras.model;

public class Item {

    public Item(int id, String descricao, int quantidade, String email, int status) {
        this.id = id;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.email = email;
        this.status = status;
    }

    public Item() {}

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

    public boolean isStatus() {
        return status == 1 ? Boolean.TRUE : Boolean.FALSE;
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

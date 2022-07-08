package com.ciandt.feedfront.models;

import com.ciandt.feedfront.interfaces.Report;

import java.io.Serializable;

public class Feedback implements Report, Serializable {

    private String id;
    private int data;
    private String autor;
    private String descricao;
    private String oQue;
    private String como;

    public Feedback(String id, int data, String autor, String descricao, String oQue, String como) {
        this.id = id;
        this.data = data;
        this.autor = autor;
        this.descricao = descricao;
        this.oQue = oQue;
        this.como = como;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getoQue() {
        return oQue;
    }

    public void setoQue(String oQue) {
        this.oQue = oQue;
    }

    public String getComo() {
        return como;
    }

    public void setComo(String como) {
        this.como = como;
    }
}

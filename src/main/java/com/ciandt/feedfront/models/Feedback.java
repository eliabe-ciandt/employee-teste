package com.ciandt.feedfront.models;

import com.ciandt.feedfront.interfaces.Report;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Feedback implements Report, Serializable {

    private String id;
    private LocalDate data;
    private Employee autor;
    private Employee proprietario;
    private String descricao;
    private String oQueMelhora;
    private String comoMelhora;
    private String arquivo;

    private static final long serialVersionID = 2L;

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao, String oQue, String como) {
        this.id = UUID.randomUUID().toString();
        this.data = data;
        this.autor = autor;
        this.proprietario = proprietario;
        this.descricao = descricao;
        this.oQueMelhora = oQue;
        this.comoMelhora = como;
        this.arquivo = getId() + ".byte";
    }

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao) {
        this.id = UUID.randomUUID().toString();
        this.data = data;
        this.autor = autor;
        this.proprietario = proprietario;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Employee getAutor() {
        return autor;
    }

    public void setAutor(Employee autor) {
        this.autor = autor;
    }

    public Employee getProprietario() {
        return proprietario;
    }

    public void setProprietario(Employee proprietario) {
        this.proprietario = proprietario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getoQueMelhora() {
        return oQueMelhora;
    }

    public void setoQueMelhora(String oQueMelhora) {
        this.oQueMelhora = oQueMelhora;
    }

    public String getComoMelhora() {
        return comoMelhora;
    }

    public void setComoMelhora(String comoMelhora) {
        this.comoMelhora = comoMelhora;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id='" + id + '\'' +
                ", data=" + data +
                ", autor=" + autor +
                ", proprietario=" + proprietario +
                ", descricao='" + descricao + '\'' +
                ", oQueMelhora='" + oQueMelhora + '\'' +
                ", comoMelhora='" + comoMelhora + '\'' +
                ", arquivo='" + arquivo + '\'' +
                '}';
    }
}

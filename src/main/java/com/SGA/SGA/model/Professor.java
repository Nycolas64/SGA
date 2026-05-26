package com.SGA.SGA.model;

import jakarta.persistence.Entity;

@Entity
public class Professor extends Usuario {

    private String titulacao;

    public Professor() {
    }

    public String getTitulacao() {
        return titulacao;
    }

    public void setTitulacao(String titulacao) {
        this.titulacao = titulacao;
    }
}
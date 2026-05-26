package com.SGA.SGA.model;

import jakarta.persistence.Entity;

@Entity
public class Coordenador extends Usuario {

    private String cursoCoordenado;

    public Coordenador() {
    }

    public String getCursoCoordenado() {
        return cursoCoordenado;
    }

    public void setCursoCoordenado(String cursoCoordenado) {
        this.cursoCoordenado = cursoCoordenado;
    }
}
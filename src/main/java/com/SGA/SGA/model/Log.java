package com.SGA.SGA.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operacao;
    private LocalDateTime dataHora;

    @ManyToOne
    private Usuario usuario;

    public Log() {
    }

    public Log(String operacao, Usuario usuario) {
        this.operacao = operacao;
        this.usuario = usuario;
        this.dataHora = LocalDateTime.now();
    }

}
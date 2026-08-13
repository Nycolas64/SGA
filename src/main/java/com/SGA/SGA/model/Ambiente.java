package com.SGA.SGA.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ambiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private Integer capacidade;
    private String equipamentos;

    public Ambiente() {
    }

    public Ambiente(String nome, String descricao, Integer capacidade, String equipamentos) {
        this.nome = nome;
        this.descricao = descricao;
        this.capacidade = capacidade;
        this.equipamentos = equipamentos;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }
    public String getEquipamentos() { return equipamentos; }
    public void setEquipamentos(String equipamentos) { this.equipamentos = equipamentos; }
}

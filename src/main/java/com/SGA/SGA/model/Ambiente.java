package com.SGA.SGA.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Ambiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private Integer capacidade;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Equipamento> equipamentos;

    public Ambiente() {
    }

    public Ambiente(String nome, String descricao, Integer capacidade, List<Equipamento> equipamentos) {
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
    public List<Equipamento> getEquipamentos() { return equipamentos; }
    public void setEquipamentos(List<Equipamento> equipamentos) { this.equipamentos = equipamentos; }
}

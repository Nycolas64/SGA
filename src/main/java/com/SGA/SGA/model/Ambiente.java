package com.SGA.SGA.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Ambiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private Integer capacidade;

    @ManyToMany
    @JoinTable(
        name = "ambiente_equipamento",
        joinColumns = @JoinColumn(name = "ambiente_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "equipamento_id", nullable = false)
    )
    private List<Equipamento> equipamentos = new ArrayList<>();

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

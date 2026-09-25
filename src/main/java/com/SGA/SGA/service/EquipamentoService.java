package com.SGA.SGA.service;

import com.SGA.SGA.model.Equipamento;
import com.SGA.SGA.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository repository;

    public List<Equipamento> listarTodos() {
        return repository.findAll();
    }

    public Optional<Equipamento> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Equipamento salvar(Equipamento equipamento) {
        return repository.save(equipamento);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}

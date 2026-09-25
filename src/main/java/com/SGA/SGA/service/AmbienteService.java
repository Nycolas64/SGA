package com.SGA.SGA.service;

import com.SGA.SGA.model.Ambiente;
import com.SGA.SGA.repository.AmbienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AmbienteService {

    @Autowired
    private AmbienteRepository repository;

    public List<Ambiente> listarTodos() {
        return repository.findAll();
    }

    public Optional<Ambiente> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Ambiente salvar(Ambiente ambiente) {
        return repository.save(ambiente);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
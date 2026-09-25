package com.SGA.SGA.controller;

import com.SGA.SGA.model.Equipamento;
import com.SGA.SGA.service.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/equipamentos")
@CrossOrigin(origins = "*")
public class EquipamentoController {

    @Autowired
    private EquipamentoService service;

    @GetMapping
    public List<Equipamento> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Equipamento buscar(@PathVariable Long id) {
        return service.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public Equipamento adicionar(@RequestBody Equipamento equipamento) {
        return service.salvar(equipamento);
    }

    @PutMapping("/{id}")
    public Equipamento editar(@PathVariable Long id, @RequestBody Equipamento equipamento) {
        equipamento.setId(id);
        return service.salvar(equipamento);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}

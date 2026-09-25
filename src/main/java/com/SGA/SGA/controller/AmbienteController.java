package com.SGA.SGA.controller;

import com.SGA.SGA.model.Ambiente;
import com.SGA.SGA.service.AmbienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ambientes")
@CrossOrigin(origins = "*")
public class AmbienteController {

    @Autowired
    private AmbienteService service;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public List<Ambiente> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Ambiente buscar(@PathVariable Long id) {
        return service.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public Ambiente adicionar(@RequestBody Ambiente ambiente) {
        return service.salvar(ambiente);
    }

    @PutMapping("/{id}")
    public Ambiente editar(@PathVariable Long id, @RequestBody Ambiente ambiente) {
        ambiente.setId(id);
        Ambiente salvo = service.salvar(ambiente);
        messagingTemplate.convertAndSend("/topic/ambientes", "AMBIENTE_ATIVO:" + id);
        return salvo;
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
        messagingTemplate.convertAndSend("/topic/ambientes", "AMBIENTE_REMOVIDO:" + id);
    }
}

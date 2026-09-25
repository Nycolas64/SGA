package com.SGA.SGA.controller;

import com.SGA.SGA.model.Solicitacao;
import com.SGA.SGA.model.StatusSolicitacao;
import com.SGA.SGA.service.SolicitacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/solicitacoes")
@CrossOrigin(origins = "*")
public class SolicitacaoController {

    private final SolicitacaoService service;

    public SolicitacaoController(SolicitacaoService service) {
        this.service = service;
    }

    @GetMapping("/pendentes")
    public List<Solicitacao> listarPendentes() {
        return service.listarPendentes();
    }

    @GetMapping("/filtro/{status}")
    public List<Solicitacao> filtrarPorStatus(@PathVariable StatusSolicitacao status) {
        return service.filtrarPorStatus(status);
    }

    @GetMapping("/{id}")
    public Solicitacao buscar(@PathVariable Long id) {
        return service.buscarPorId(id).orElse(null);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @RequestBody StatusSolicitacao novoStatus) {
        try {
            Solicitacao sol = service.atualizarStatus(id, novoStatus);
            return ResponseEntity.ok(sol);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
package com.SGA.SGA.service;

import com.SGA.SGA.model.*;
import com.SGA.SGA.repository.SolicitacaoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository repository;

    public SolicitacaoService(SolicitacaoRepository repository) {
        this.repository = repository;
    }

    public List<Solicitacao> listarPendentes() {
        return repository.findByStatus(StatusSolicitacao.PENDENTE);
    }

    public List<Solicitacao> filtrarPorStatus(StatusSolicitacao status) {
        return repository.findByStatus(status);
    }

    public Optional<Solicitacao> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Solicitacao atualizarStatus(Long id, StatusSolicitacao novoStatus) {
        Solicitacao sol = repository.findById(id).orElseThrow();

        if (novoStatus == StatusSolicitacao.APROVADA) {
            boolean conflito = repository.existsByAmbienteAndDataAndStatusAndHorarioInicioBeforeAndHorarioFimAfter(
                sol.getAmbiente(), sol.getData(), StatusSolicitacao.APROVADA, sol.getHorarioFim(), sol.getHorarioInicio()
            );

            if (conflito) {
                throw new RuntimeException("Conflito de horário identificado para este ambiente.");
            }
        }

        sol.setStatus(novoStatus);
        return repository.save(sol);
    }
}

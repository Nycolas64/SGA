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
        Optional<Solicitacao> opt = repository.findById(id);
        if (opt.isPresent()) {
            Solicitacao sol = opt.get();
            if (sol.getStatus() == StatusSolicitacao.PENDENTE) {
                sol.setStatus(StatusSolicitacao.EM_ANALISE);
                repository.save(sol);
            }
        }
        return opt;
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

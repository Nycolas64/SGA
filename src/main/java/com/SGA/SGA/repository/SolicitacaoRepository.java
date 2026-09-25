package com.SGA.SGA.repository;

import com.SGA.SGA.model.Ambiente;
import com.SGA.SGA.model.Solicitacao;
import com.SGA.SGA.model.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    List<Solicitacao> findByStatus(StatusSolicitacao status);

    boolean existsByAmbienteAndDataAndStatusAndHorarioInicioBeforeAndHorarioFimAfter(
            Ambiente ambiente,
            LocalDate data,
            StatusSolicitacao status,
            LocalTime horarioFim,
            LocalTime horarioInicio
    );
}
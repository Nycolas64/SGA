package com.SGA.SGA.config;

import com.SGA.SGA.model.*;
import com.SGA.SGA.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final AmbienteRepository ambienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final SolicitacaoRepository solicitacaoRepository;

    public DataLoader(AmbienteRepository ambienteRepository,
                      UsuarioRepository usuarioRepository,
                      SolicitacaoRepository solicitacaoRepository) {
        this.ambienteRepository = ambienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.solicitacaoRepository = solicitacaoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        solicitacaoRepository.deleteAll();
        usuarioRepository.deleteAll();
        ambienteRepository.deleteAll();

        Equipamento projetor = new Equipamento();
        projetor.setNome("Projetor");

        Equipamento tv = new Equipamento();
        tv.setNome("TV");

        Equipamento microfone = new Equipamento();
        microfone.setNome("Microfone");

        Equipamento computadores = new Equipamento();
        computadores.setNome("Computadores");

        Equipamento microscopios = new Equipamento();
        microscopios.setNome("Microscópios");

        Ambiente s1 = new Ambiente("Sala 1", "Capacidade 40, Projetor.", 40, List.of(projetor));
        Ambiente s2 = new Ambiente("Sala 2", "Capacidade 30, TV, microfone.", 30, List.of(tv, microfone));
        Ambiente inf = new Ambiente("Informática", "Capacidade 20, computadores.", 20, List.of(computadores, tv));
        Ambiente lab = new Ambiente("Laboratório", "Capacidade 35, microscópios.", 35, List.of(microscopios, tv));
        
        ambienteRepository.saveAll(List.of(s1, s2, inf, lab));

        Professor p1 = new Professor(); p1.setNome("João"); p1.setEmail("joao@unifil.br"); p1.setTitulacao("Mestre");
        Professor p2 = new Professor(); p2.setNome("Alice"); p2.setEmail("alice@unifil.br"); p2.setTitulacao("Doutora");
        Professor p3 = new Professor(); p3.setNome("Maria"); p3.setEmail("maria@unifil.br"); p3.setTitulacao("Especialista");
        Professor p4 = new Professor(); p4.setNome("Pedro"); p4.setEmail("pedro@unifil.br"); p4.setTitulacao("Mestre");
        Professor p5 = new Professor(); p5.setNome("Marcos"); p5.setEmail("marcos@unifil.br"); p5.setTitulacao("Especialista");
        usuarioRepository.saveAll(List.of(p1, p2, p3, p4, p5));

        Solicitacao sol1 = new Solicitacao();
        sol1.setProfessor(p1); sol1.setAmbiente(s2); sol1.setData(LocalDate.now().plusDays(2));
        sol1.setHorarioInicio(LocalTime.of(13, 0)); sol1.setHorarioFim(LocalTime.of(15, 0));
        sol1.setPublicoEsperado(20); sol1.setMotivo("Aula de algoritmos.");
        sol1.setStatus(StatusSolicitacao.PENDENTE);

        Solicitacao sol2 = new Solicitacao();
        sol2.setProfessor(p3); sol2.setAmbiente(s2); sol2.setData(LocalDate.now().plusDays(2));
        sol2.setHorarioInicio(LocalTime.of(14, 0)); sol2.setHorarioFim(LocalTime.of(16, 0));
        sol2.setPublicoEsperado(25); sol2.setMotivo("Conflito proposital com o João.");
        sol2.setStatus(StatusSolicitacao.PENDENTE);

        Solicitacao sol3 = new Solicitacao();
        sol3.setProfessor(p2); sol3.setAmbiente(lab); sol3.setData(LocalDate.now().plusDays(1));
        sol3.setHorarioInicio(LocalTime.of(14, 0)); sol3.setHorarioFim(LocalTime.of(15, 0));
        sol3.setPublicoEsperado(15); sol3.setMotivo("Aula prática de biologia.");
        sol3.setStatus(StatusSolicitacao.PENDENTE);

        Solicitacao sol4 = new Solicitacao();
        sol4.setProfessor(p4); sol4.setAmbiente(inf); sol4.setData(LocalDate.now().plusDays(3));
        sol4.setHorarioInicio(LocalTime.of(10, 0)); sol4.setHorarioFim(LocalTime.of(12, 0));
        sol4.setPublicoEsperado(18); sol4.setMotivo("Treinamento de sistema.");
        sol4.setStatus(StatusSolicitacao.PENDENTE);

        Solicitacao solExpirada = new Solicitacao();
        solExpirada.setProfessor(p5); solExpirada.setAmbiente(s1); solExpirada.setData(LocalDate.now().minusDays(5));
        solExpirada.setHorarioInicio(LocalTime.of(9, 0)); solExpirada.setHorarioFim(LocalTime.of(11, 0));
        solExpirada.setPublicoEsperado(30); solExpirada.setMotivo("Reserva antiga.");
        solExpirada.setStatus(StatusSolicitacao.EXPIRADA);

        solicitacaoRepository.saveAll(List.of(sol1, sol2, sol3, sol4, solExpirada));
    }
}

package com.medmonstros.services;

import com.medmonstros.entities.*;
import com.medmonstros.enums.StatusConsulta;
import com.medmonstros.exceptions.RecursoNaoEncontradoException;
import com.medmonstros.exceptions.RegraNegocioException;
import com.medmonstros.repositories.ConsultaRepository;
import com.medmonstros.repositories.HorarioDisponivelRepository;
import com.medmonstros.repositories.MedicoRepository;
import com.medmonstros.repositories.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final HorarioDisponivelRepository horarioRepository;

    public ConsultaService(ConsultaRepository consultaRepository,
                           PacienteRepository pacienteRepository,
                           MedicoRepository medicoRepository,
                           HorarioDisponivelRepository horarioRepository) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.horarioRepository = horarioRepository;
    }

    /**
     * Parte 1: Solicitar Consulta
     * 
     * 1. Busca paciente, médico e horário
     * 2. Valida que o horário está livre (RN03 parcial)
     * 3. Valida que o médico não tem outra consulta ACEITA no mesmo horário (RN03 completo)
     * 4. Chama paciente.validarAgendamento() para validações por espécie (RN07/RN08/RN09)
     * 5. Cria Consulta com status SOLICITADA
     */
    @Transactional
    public Consulta solicitarConsulta(Long pacienteId, Long medicoId, Long horarioId) {
        // 1. Buscar entidades
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado."));
        
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Médico não encontrado."));
        
        HorarioDisponivel horario = horarioRepository.findById(horarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Horário não encontrado."));

        // 2. Validar que o horário está livre (RN03 parcial)
        if (horario.isOcupado()) {
            throw new RegraNegocioException("Horário já está ocupado.");
        }

        // 3. Validar que o médico não tem outra consulta ACEITA no mesmo horário (RN03 completo)
        boolean medicoComConsultaAceitaNesseHorario = consultaRepository.findAll().stream()
                .anyMatch(c -> 
                    c.getMedico().getId().equals(medicoId) &&
                    c.getStatus() == StatusConsulta.ACEITA &&
                    c.getHorario().getId().equals(horarioId)
                );
        
        if (medicoComConsultaAceitaNesseHorario) {
            throw new RegraNegocioException("Médico já possui uma consulta aceita neste horário.");
        }

        // 4. Chamar validação por espécie (polimorfismo) (RN07/RN08/RN09)
        paciente.validarAgendamento(horario, medico);

        // 5. Criar consulta com status SOLICITADA
        Consulta consulta = new Consulta(paciente, medico, horario);
        return consultaRepository.save(consulta);
    }

    /**
     * Obter uma consulta por ID
     */
    public Consulta obterConsulta(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada."));
    }

    /**
     * Histórico de consultas do paciente (RF07)
     */
    public List<Consulta> obterPorPaciente(Long pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado."));
        
        return consultaRepository.findByPaciente(paciente);
    }

    /**
     * Consultas do médico (RF07)
     */
    public List<Consulta> obterPorMedico(Long medicoId) {
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Médico não encontrado."));
        
        return consultaRepository.findByMedico(medico);
    }

    /**
     * Parte 2: Máquina de Estados - Aceitar consulta
     * 
     * Transição: SOLICITADA → ACEITA
     * Efeito colateral: horário passa a ocupado=true
     */
    @Transactional
    public Consulta aceitar(Long consultaId) {
        Consulta consulta = obterConsulta(consultaId);

        if (consulta.getHorario().isOcupado()) {
            throw new RegraNegocioException("Horário já está ocupado.");
        }

        boolean jaExisteConsultaAceita = consultaRepository.findAll().stream()
                .anyMatch(c ->
                        !c.getId().equals(consulta.getId()) &&
                        c.getMedico().getId().equals(consulta.getMedico().getId()) &&
                        c.getStatus() == StatusConsulta.ACEITA &&
                        c.getHorario().getId().equals(consulta.getHorario().getId()));

        if (jaExisteConsultaAceita) {
            throw new RegraNegocioException("Médico já possui uma consulta aceita neste horário.");
        }
        
        // A entidade já faz a validação de transição
        consulta.aceitar();
        
        // Marcar horário como ocupado
        consulta.getHorario().setOcupado(true);
        horarioRepository.save(consulta.getHorario());
        
        return consultaRepository.save(consulta);
    }

    /**
     * Parte 2: Máquina de Estados - Recusar consulta
     * 
     * Transição: SOLICITADA → RECUSADA
     */
    @Transactional
    public Consulta recusar(Long consultaId) {
        Consulta consulta = obterConsulta(consultaId);
        consulta.recusar();
        return consultaRepository.save(consulta);
    }

    /**
     * Parte 2: Máquina de Estados - Cancelar consulta
     * 
     * Transições:
     * - SOLICITADA → CANCELADA
     * - ACEITA → CANCELADA
     * 
     * Efeito colateral: Se horário estava ocupado, libera horário (ocupado=false)
     */
    @Transactional
    public Consulta cancelar(Long consultaId) {
        Consulta consulta = obterConsulta(consultaId);
        
        // Liberar horário se estava ocupado
        if (consulta.getHorario().isOcupado()) {
            consulta.getHorario().setOcupado(false);
            horarioRepository.save(consulta.getHorario());
        }
        
        consulta.cancelar();
        return consultaRepository.save(consulta);
    }

    /**
     * Parte 2: Máquina de Estados - Realizar consulta
     * 
     * Transição: ACEITA → REALIZADA
     */
    @Transactional
    public Consulta realizar(Long consultaId) {
        Consulta consulta = obterConsulta(consultaId);
        consulta.realizar();
        return consultaRepository.save(consulta);
    }

    /**
     * Parte 3: Registrar Tratamento
     * 
     * Valida que a consulta está em status REALIZADA (RN10)
     * Instancia TratamentoMagico ou TratamentoEspiritual conforme tipo (polimorfismo)
     * Calcula o custo via método polimórfico
     */
    @Transactional
    public Consulta registrarTratamento(Long consultaId, String tipo, String descricao, 
                                        Double valorBase, Integer nivelEncantamento, 
                                        Boolean requerExorcismo) {
        Consulta consulta = obterConsulta(consultaId);
        
        // RN10: Tratamento só pode ser registrado após REALIZADA
        if (consulta.getStatus() != StatusConsulta.REALIZADA) {
            throw new RegraNegocioException("Tratamento só pode ser registrado após a realização da consulta.");
        }

        Tratamento tratamento;

        if ("MAGICO".equalsIgnoreCase(tipo)) {
            // Polimorfismo: instancia TratamentoMagico
            if (nivelEncantamento == null) {
                throw new RegraNegocioException("nivelEncantamento é obrigatório para tratamento MAGICO.");
            }
            tratamento = new TratamentoMagico(descricao, valorBase, nivelEncantamento);
        } else if ("ESPIRITUAL".equalsIgnoreCase(tipo)) {
            // Polimorfismo: instancia TratamentoEspiritual
            if (requerExorcismo == null) {
                throw new RegraNegocioException("requerExorcismo é obrigatório para tratamento ESPIRITUAL.");
            }
            tratamento = new TratamentoEspiritual(descricao, valorBase, requerExorcismo);
        } else {
            throw new RegraNegocioException("Tipo de tratamento inválido. Use MAGICO ou ESPIRITUAL.");
        }

        // Registrar tratamento na consulta (valida internamente)
        consulta.registrarTratamento(tratamento);
        
        return consultaRepository.save(consulta);
    }
}

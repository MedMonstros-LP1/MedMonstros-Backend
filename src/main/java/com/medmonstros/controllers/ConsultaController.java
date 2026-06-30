package com.medmonstros.controllers;

import com.medmonstros.dtos.ConsultaDetailDTO;
import com.medmonstros.dtos.ConsultaRequestDTO;
import com.medmonstros.dtos.ConsultaResumoDTO;
import com.medmonstros.dtos.TratamentoRequestDTO;
import com.medmonstros.entities.Consulta;
import com.medmonstros.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    /**
     * Parte 1: POST /api/consultas
     * Solicitar consulta
     * 
     * Body: { "pacienteId": 1, "medicoId": 1, "horarioId": 1 }
     * Resposta 201
     */
    @PostMapping
    public ResponseEntity<ConsultaDetailDTO> solicitarConsulta(@Valid @RequestBody ConsultaRequestDTO dto) {
        Consulta consulta = consultaService.solicitarConsulta(dto.pacienteId(), dto.medicoId(), dto.horarioId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ConsultaDetailDTO.de(consulta));
    }

    /**
     * Parte 1: GET /api/consultas/{id}
     * Obter detalhe da consulta com tratamento embutido (se já registrado)
     * 
     * Resposta 200
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDetailDTO> obterConsulta(@PathVariable Long id) {
        Consulta consulta = consultaService.obterConsulta(id);
        return ResponseEntity.ok(ConsultaDetailDTO.de(consulta));
    }

    /**
     * Parte 1: GET /api/consultas/paciente/{pacienteId}
     * Histórico de consultas do paciente (RF07)
     * 
     * Resposta 200 - lista
     */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ConsultaResumoDTO>> obterPorPaciente(@PathVariable Long pacienteId) {
        List<Consulta> consultas = consultaService.obterPorPaciente(pacienteId);
        List<ConsultaResumoDTO> dtos = consultas.stream()
                .map(ConsultaResumoDTO::de)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Parte 1: GET /api/consultas/medico/{medicoId}
     * Consultas do médico (RF07)
     * 
     * Resposta 200 - lista
     */
    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<ConsultaResumoDTO>> obterPorMedico(@PathVariable Long medicoId) {
        List<Consulta> consultas = consultaService.obterPorMedico(medicoId);
        List<ConsultaResumoDTO> dtos = consultas.stream()
                .map(ConsultaResumoDTO::de)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Parte 2: PATCH /api/consultas/{id}/aceitar
     * Médico aceita; horário passa a ocupado=true
     * 
     * Resposta 200
     */
    @PatchMapping("/{id}/aceitar")
    public ResponseEntity<ConsultaDetailDTO> aceitar(@PathVariable Long id) {
        Consulta consulta = consultaService.aceitar(id);
        return ResponseEntity.ok(ConsultaDetailDTO.de(consulta));
    }

    /**
     * Parte 2: PATCH /api/consultas/{id}/recusar
     * Médico recusa
     * 
     * Resposta 200
     */
    @PatchMapping("/{id}/recusar")
    public ResponseEntity<ConsultaDetailDTO> recusar(@PathVariable Long id) {
        Consulta consulta = consultaService.recusar(id);
        return ResponseEntity.ok(ConsultaDetailDTO.de(consulta));
    }

    /**
     * Parte 2: PATCH /api/consultas/{id}/cancelar
     * Paciente ou médico cancela (conforme status permitido)
     * 
     * Resposta 200
     */
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ConsultaDetailDTO> cancelar(@PathVariable Long id) {
        Consulta consulta = consultaService.cancelar(id);
        return ResponseEntity.ok(ConsultaDetailDTO.de(consulta));
    }

    /**
     * Parte 2: PATCH /api/consultas/{id}/realizar
     * Médico realiza
     * 
     * Resposta 200
     */
    @PatchMapping("/{id}/realizar")
    public ResponseEntity<ConsultaDetailDTO> realizar(@PathVariable Long id) {
        Consulta consulta = consultaService.realizar(id);
        return ResponseEntity.ok(ConsultaDetailDTO.de(consulta));
    }

    /**
     * Parte 3: POST /api/consultas/{id}/tratamento
     * Registrar tratamento
     * 
     * Body (MAGICO):
     * { "tipo": "MAGICO", "descricao": "Transfusao encantada", "valorBase": 200.0, "nivelEncantamento": 3 }
     * 
     * Body (ESPIRITUAL):
     * { "tipo": "ESPIRITUAL", "descricao": "Sessao de purificacao", "valorBase": 150.0, "requerExorcismo": true }
     * 
     * Resposta 201 — retorna a consulta atualizada com tratamento e custo calculado
     */
    @PostMapping("/{id}/tratamento")
    public ResponseEntity<ConsultaDetailDTO> registrarTratamento(
            @PathVariable Long id,
            @Valid @RequestBody TratamentoRequestDTO dto) {
        
        Consulta consulta = consultaService.registrarTratamento(
                id,
                dto.tipo(),
                dto.descricao(),
                dto.valorBase(),
                dto.nivelEncantamento(),
                dto.requerExorcismo()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(ConsultaDetailDTO.de(consulta));
    }
}

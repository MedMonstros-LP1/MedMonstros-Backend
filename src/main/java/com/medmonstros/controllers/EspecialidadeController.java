package com.medmonstros.controllers;

import com.medmonstros.dtos.EspecialidadeRequestDTO;
import com.medmonstros.dtos.EspecialidadeResponseDTO;
import com.medmonstros.services.EspecialidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadeController {

    private final EspecialidadeService service;

    public EspecialidadeController(EspecialidadeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadeResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EspecialidadeResponseDTO> criar(@RequestBody @Valid EspecialidadeRequestDTO dto) {
        EspecialidadeResponseDTO novaEspecialidade = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEspecialidade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid EspecialidadeRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
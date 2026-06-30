package com.medmonstros.controllers;

import com.medmonstros.dtos.HorarioRequestDTO;
import com.medmonstros.dtos.HorarioResponseDTO;
import com.medmonstros.services.HorarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    private final HorarioService service;

    public HorarioController(HorarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<HorarioResponseDTO> criar(@RequestBody @Valid HorarioRequestDTO dto) {
        HorarioResponseDTO novoHorario = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoHorario);
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<HorarioResponseDTO>> listarLivres(@PathVariable Long medicoId) {
        return ResponseEntity.ok(service.listarLivres(medicoId));
    }

    @GetMapping("/medico/{medicoId}/todos")
    public ResponseEntity<List<HorarioResponseDTO>> listarTodos(@PathVariable Long medicoId) {
        return ResponseEntity.ok(service.listarTodos(medicoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

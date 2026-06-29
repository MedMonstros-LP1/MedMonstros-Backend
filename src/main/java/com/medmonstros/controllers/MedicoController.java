package com.medmonstros.controllers;

import com.medmonstros.dtos.MedicoDetalheDTO;
import com.medmonstros.dtos.MedicoResumoDTO;
import com.medmonstros.services.MedicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    private final MedicoService service;

    public MedicoController(MedicoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MedicoResumoDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDetalheDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarDetalhesPorId(id));
    }
}
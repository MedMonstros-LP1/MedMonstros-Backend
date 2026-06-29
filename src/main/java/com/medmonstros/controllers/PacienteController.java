package com.medmonstros.controllers;

import com.medmonstros.dtos.PacienteDetalheDTO;
import com.medmonstros.dtos.PacienteResumoDTO;
import com.medmonstros.services.PacienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public List<PacienteResumoDTO> listar() {
        return pacienteService.listarTodos().stream()
                .map(PacienteResumoDTO::de)
                .toList();
    }

    @GetMapping("/{id}")
    public PacienteDetalheDTO detalhe(@PathVariable Long id) {
        return PacienteDetalheDTO.de(pacienteService.buscarPorId(id));
    }
}

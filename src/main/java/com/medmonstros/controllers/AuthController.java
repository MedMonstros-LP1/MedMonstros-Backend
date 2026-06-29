package com.medmonstros.controllers;

import com.medmonstros.dtos.LoginDTO;
import com.medmonstros.dtos.PerfilDTO;
import com.medmonstros.dtos.RegistroMedicoDTO;
import com.medmonstros.dtos.RegistroPacienteDTO;
import com.medmonstros.services.AuthService;
import com.medmonstros.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final PacienteService pacienteService;

    public AuthController(AuthService authService, PacienteService pacienteService) {
        this.authService = authService;
        this.pacienteService = pacienteService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<PerfilDTO> registrarMedico(@Valid @RequestBody RegistroMedicoDTO dto) {
        PerfilDTO perfil = PerfilDTO.de(authService.registrarMedico(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(perfil);
    }

    @PostMapping("/registrar-paciente")
    public ResponseEntity<PerfilDTO> registrarPaciente(@Valid @RequestBody RegistroPacienteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(PerfilDTO.de(pacienteService.registrar(dto)));
    }

    @PostMapping("/login")
    public PerfilDTO login(@Valid @RequestBody LoginDTO dto) {
        return PerfilDTO.de(authService.login(dto));
    }
}

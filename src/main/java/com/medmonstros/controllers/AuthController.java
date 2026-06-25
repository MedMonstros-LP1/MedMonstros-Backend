package com.medmonstros.controllers;

import com.medmonstros.dtos.LoginDTO;
import com.medmonstros.dtos.PerfilDTO;
import com.medmonstros.dtos.RegistroMedicoDTO;
import com.medmonstros.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<PerfilDTO> registrarMedico(@Valid @RequestBody RegistroMedicoDTO dto) {
        PerfilDTO perfil = PerfilDTO.de(authService.registrarMedico(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(perfil);
    }

    @PostMapping("/login")
    public PerfilDTO login(@Valid @RequestBody LoginDTO dto) {
        return PerfilDTO.de(authService.login(dto));
    }
}

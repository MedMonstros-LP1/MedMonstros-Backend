package com.medmonstros.dtos;

import com.medmonstros.entities.Paciente;

public record PacienteResumoDTO(
        Long id,
        String nome,
        String email,
        String especie,
        String tipo
) {
    public static PacienteResumoDTO de(Paciente p) {
        return new PacienteResumoDTO(p.getId(), p.getNome(), p.getEmail(), p.especie(), p.tipoUsuario());
    }
}

package com.medmonstros.dtos;

import com.medmonstros.entities.Paciente;
import com.medmonstros.entities.Usuario;

public record PerfilDTO(
        Long id,
        String nome,
        String email,
        String tipo
) {

    public static PerfilDTO de(Usuario u) {
        String tipo = (u instanceof Paciente p) ? p.especie().toUpperCase() : u.tipoUsuario();
        return new PerfilDTO(u.getId(), u.getNome(), u.getEmail(), tipo);
    }
}

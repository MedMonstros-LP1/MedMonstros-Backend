package com.medmonstros.dtos;

import com.medmonstros.entities.Usuario;

public record PerfilDTO(
        Long id,
        String nome,
        String email,
        String tipo
) {

    public static PerfilDTO de(Usuario u) {
        return new PerfilDTO(u.getId(), u.getNome(), u.getEmail(), u.tipoUsuario());
    }
}

package com.medmonstros.dtos;

import com.medmonstros.entities.Medico;
import java.util.Set;
import java.util.stream.Collectors;

public record MedicoDetalheDTO(
    Long id,
    String nome,
    String email,
    String registroConselho,
    Set<EspecialidadeResponseDTO> especialidades
) {
    public MedicoDetalheDTO(Medico medico) {
        this(
            medico.getId(),
            medico.getNome(),
            medico.getEmail(),
            medico.getRegistroConselho(),
            medico.getEspecialidades().stream()
                .map(EspecialidadeResponseDTO::new)
                .collect(Collectors.toSet())
        );
    }
}
package com.medmonstros.dtos;

import com.medmonstros.entities.Especialidade;

public record EspecialidadeResponseDTO(
    Long id,
    String nome,
    Boolean atendeEtereos
) {
    public EspecialidadeResponseDTO(Especialidade especialidade) {
        this(especialidade.getId(), especialidade.getNome(), especialidade.isAtendeEtereos());
    }
}
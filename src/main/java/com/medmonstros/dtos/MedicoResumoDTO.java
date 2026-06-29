package com.medmonstros.dtos;

import com.medmonstros.entities.Medico;

public record MedicoResumoDTO(
    Long id,
    String nome,
    String registroConselho // O CRM dos monstros
) {
    public MedicoResumoDTO(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getRegistroConselho());
    }
}
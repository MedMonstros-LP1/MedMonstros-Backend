package com.medmonstros.dtos;

import com.medmonstros.entities.HorarioDisponivel;
import java.time.LocalDateTime;

public record HorarioResponseDTO(
    Long id,
    Long medicoId,
    LocalDateTime inicio,
    LocalDateTime fim,
    boolean luaCheia,
    boolean ocupado
) {
    public HorarioResponseDTO(HorarioDisponivel h) {
        this(h.getId(), h.getMedico().getId(), h.getInicio(), h.getFim(), h.isLuaCheia(), h.isOcupado());
    }
}

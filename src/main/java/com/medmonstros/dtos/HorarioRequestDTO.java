package com.medmonstros.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record HorarioRequestDTO(
    @NotNull(message = "O ID do médico é obrigatório")
    Long medicoId,

    @NotNull(message = "O horário de início é obrigatório")
    LocalDateTime inicio,

    @NotNull(message = "O horário de fim é obrigatório")
    LocalDateTime fim,

    boolean luaCheia
) {
    public HorarioRequestDTO {
        // luaCheia defaults to false when not provided (primitive boolean default)
    }
}

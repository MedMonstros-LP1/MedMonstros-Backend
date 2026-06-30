package com.medmonstros.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ConsultaRequestDTO(
        @NotNull(message = "pacienteId é obrigatório")
        @Positive(message = "pacienteId deve ser positivo")
        Long pacienteId,
        
        @NotNull(message = "medicoId é obrigatório")
        @Positive(message = "medicoId deve ser positivo")
        Long medicoId,
        
        @NotNull(message = "horarioId é obrigatório")
        @Positive(message = "horarioId deve ser positivo")
        Long horarioId
) {}

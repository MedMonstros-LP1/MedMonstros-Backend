package com.medmonstros.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TratamentoRequestDTO(
        @NotBlank(message = "tipo é obrigatório")
        String tipo,
        
        @NotBlank(message = "descricao é obrigatória")
        String descricao,
        
        @NotNull(message = "valorBase é obrigatório")
        @Positive(message = "valorBase deve ser positivo")
        Double valorBase,
        
        Integer nivelEncantamento,
        
        Boolean requerExorcismo
) {}

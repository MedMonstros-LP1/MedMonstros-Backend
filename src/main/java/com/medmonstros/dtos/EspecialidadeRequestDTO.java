package com.medmonstros.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EspecialidadeRequestDTO(
    @NotBlank(message = "O nome da especialidade é obrigatório.") 
    String nome,
    
    @NotNull(message = "Informe se a especialidade atende seres etéreos (fantasmas, espectros, etc).") 
    Boolean atendeEtereos
) {}
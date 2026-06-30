package com.medmonstros.dtos;

import com.medmonstros.enums.EspeciePaciente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistroPacienteDTO(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String senha,
        @NotNull EspeciePaciente especie,
        Boolean toleranciaSolar,
        Boolean controlaTransformacao,
        Integer anosAssombrando
) {}

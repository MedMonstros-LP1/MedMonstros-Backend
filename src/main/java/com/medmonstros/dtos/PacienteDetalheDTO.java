package com.medmonstros.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.medmonstros.entities.Fantasma;
import com.medmonstros.entities.Lobisomem;
import com.medmonstros.entities.Paciente;
import com.medmonstros.entities.Vampiro;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PacienteDetalheDTO(
        Long id,
        String nome,
        String email,
        String especie,
        String tipo,
        Boolean toleranciaSolar,
        Boolean controlaTransformacao,
        Integer anosAssombrando
) {
    public static PacienteDetalheDTO de(Paciente p) {
        Boolean toleranciaSolar = null;
        Boolean controlaTransformacao = null;
        Integer anosAssombrando = null;
        if (p instanceof Vampiro v) toleranciaSolar = v.isToleranciaSolar();
        else if (p instanceof Lobisomem l) controlaTransformacao = l.isControlaTransformacao();
        else if (p instanceof Fantasma f) anosAssombrando = f.getAnosAssombrando();
        return new PacienteDetalheDTO(
                p.getId(), p.getNome(), p.getEmail(), p.especie(), p.tipoUsuario(),
                toleranciaSolar, controlaTransformacao, anosAssombrando
        );
    }
}

package com.medmonstros.dtos;

import com.medmonstros.entities.Consulta;
import com.medmonstros.enums.StatusConsulta;

import java.time.LocalDateTime;

public record ConsultaResumoDTO(
        Long id,
        StatusConsulta status,
        String pacienteNome,
        String medicoNome,
        LocalDateTime horarioInicio,
        boolean temTratamento
) {
    public static ConsultaResumoDTO de(Consulta consulta) {
        return new ConsultaResumoDTO(
                consulta.getId(),
                consulta.getStatus(),
                consulta.getPaciente().getNome(),
                consulta.getMedico().getNome(),
                consulta.getHorario().getInicio(),
                consulta.getTratamento() != null
        );
    }
}

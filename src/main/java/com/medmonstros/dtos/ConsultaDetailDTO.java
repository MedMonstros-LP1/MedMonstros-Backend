package com.medmonstros.dtos;

import com.medmonstros.entities.Consulta;
import com.medmonstros.entities.Tratamento;
import com.medmonstros.enums.StatusConsulta;

import java.time.LocalDateTime;

public record ConsultaDetailDTO(
        Long id,
        StatusConsulta status,
        PacienteDetalheDTO paciente,
        MedicoDetalheDTO medico,
        HorarioDTO horario,
        TratamentoResponseDTO tratamento
) {
    public static ConsultaDetailDTO de(Consulta consulta) {
        HorarioDTO horarioDTO;
        if (consulta.getHorario() != null) {
            horarioDTO = new HorarioDTO(
                    consulta.getHorario().getId(),
                    consulta.getHorario().getInicio(),
                    consulta.getHorario().getFim(),
                    consulta.getHorario().isLuaCheia(),
                    consulta.getHorario().isOcupado()
            );
        } else {
            horarioDTO = new HorarioDTO(null, consulta.getHorarioInicio(), null, false, false);
        }

        TratamentoResponseDTO tratamentoDTO = consulta.getTratamento() != null ?
                TratamentoResponseDTO.de(consulta.getTratamento()) : null;

        return new ConsultaDetailDTO(
                consulta.getId(),
                consulta.getStatus(),
                PacienteDetalheDTO.de((com.medmonstros.entities.Paciente) consulta.getPaciente()),
                new MedicoDetalheDTO(consulta.getMedico()),
                horarioDTO,
                tratamentoDTO
        );
    }
}

record HorarioDTO(
        Long id,
        LocalDateTime inicio,
        LocalDateTime fim,
        boolean luaCheia,
        boolean ocupado
) {}

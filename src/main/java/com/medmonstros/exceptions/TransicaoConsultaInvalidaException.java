package com.medmonstros.exceptions;

import com.medmonstros.enums.StatusConsulta;

public class TransicaoConsultaInvalidaException extends RegraNegocioException {
    public TransicaoConsultaInvalidaException(StatusConsulta atual, StatusConsulta destino) {
        super("Transição de consulta inválida: " + atual + " -> " + destino);
    }
}

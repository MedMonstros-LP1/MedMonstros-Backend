package com.medmonstros.dtos;

import com.medmonstros.entities.Tratamento;
import com.medmonstros.entities.TratamentoMagico;
import com.medmonstros.entities.TratamentoEspiritual;

public record TratamentoResponseDTO(
        String tipo,
        String descricao,
        Double valorBase,
        Double custo,
        Integer nivelEncantamento,
        Boolean requerExorcismo
) {
    public static TratamentoResponseDTO de(Tratamento tratamento) {
        if (tratamento instanceof TratamentoMagico tm) {
            return new TratamentoResponseDTO(
                    "MAGICO",
                    tm.getDescricao(),
                    tm.getValorBase(),
                    tm.calcularCusto(),
                    tm.getNivelEncantamento(),
                    null
            );
        } else if (tratamento instanceof TratamentoEspiritual te) {
            return new TratamentoResponseDTO(
                    "ESPIRITUAL",
                    te.getDescricao(),
                    te.getValorBase(),
                    te.calcularCusto(),
                    null,
                    te.isRequerExorcismo()
            );
        }
        return null;
    }
}

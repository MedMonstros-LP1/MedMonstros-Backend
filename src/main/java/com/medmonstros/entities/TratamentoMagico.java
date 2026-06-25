package com.medmonstros.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("MAGICO")
@Getter @Setter @NoArgsConstructor
public class TratamentoMagico extends Tratamento {

    private int nivelEncantamento;

    public TratamentoMagico(String descricao, double valorBase, int nivelEncantamento) {
        super(descricao, valorBase);
        this.nivelEncantamento = nivelEncantamento;
    }

    @Override
    public double calcularCusto() {
        return getValorBase() * (1 + nivelEncantamento * 0.15);
    }

    @Override
    public String protocolo() {
        return "Protocolo mágico nível " + nivelEncantamento;
    }
}

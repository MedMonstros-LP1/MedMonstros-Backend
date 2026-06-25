package com.medmonstros.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("ESPIRITUAL")
@Getter @Setter @NoArgsConstructor
public class TratamentoEspiritual extends Tratamento {

    private boolean requerExorcismo;

    public TratamentoEspiritual(String descricao, double valorBase, boolean requerExorcismo) {
        super(descricao, valorBase);
        this.requerExorcismo = requerExorcismo;
    }

    @Override
    public double calcularCusto() {
        return getValorBase() + (requerExorcismo ? 500.0 : 0.0);
    }

    @Override
    public String protocolo() {
        return requerExorcismo ? "Sessão espiritual com exorcismo" : "Sessão espiritual padrão";
    }
}

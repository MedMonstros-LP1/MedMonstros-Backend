package com.medmonstros.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Polimorfismo: cada tipo de tratamento calcula custo e protocolo de forma diferente. */
@Entity
@Table(name = "tratamento")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
@Getter @Setter @NoArgsConstructor
public abstract class Tratamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private double valorBase;

    protected Tratamento(String descricao, double valorBase) {
        this.descricao = descricao;
        this.valorBase = valorBase;
    }

    public abstract double calcularCusto();
    public abstract String protocolo();
}

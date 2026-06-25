package com.medmonstros.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "especialidade")
@Getter @Setter @NoArgsConstructor
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    private boolean atendeEtereos = false;

    public Especialidade(String nome, boolean atendeEtereos) {
        this.nome = nome;
        this.atendeEtereos = atendeEtereos;
    }
}

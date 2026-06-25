package com.medmonstros.entities;

import com.medmonstros.exceptions.RegraEspecieException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("LOBISOMEM")
@Getter @Setter @NoArgsConstructor
public class Lobisomem extends Paciente {

    private boolean controlaTransformacao = false;

    public Lobisomem(String nome, String email, String senhaHash, boolean controlaTransformacao) {
        super(nome, email, senhaHash);
        this.controlaTransformacao = controlaTransformacao;
    }

    @Override
    public void validarAgendamento(HorarioDisponivel horario, Medico medico) {
        if (horario.isLuaCheia()) {
            throw new RegraEspecieException("Lobisomens nao podem agendar consultas em noites de lua cheia.");
        }
    }

    @Override
    public String especie() { return "Lobisomem"; }
}

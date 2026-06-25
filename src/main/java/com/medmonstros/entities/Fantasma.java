package com.medmonstros.entities;

import com.medmonstros.exceptions.RegraEspecieException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("FANTASMA")
@Getter @Setter @NoArgsConstructor
public class Fantasma extends Paciente {

    private int anosAssombrando;

    public Fantasma(String nome, String email, String senhaHash, int anosAssombrando) {
        super(nome, email, senhaHash);
        this.anosAssombrando = anosAssombrando;
    }

    @Override
    public void validarAgendamento(HorarioDisponivel horario, Medico medico) {
        boolean atendeEtereos = medico.getEspecialidades().stream()
                .anyMatch(Especialidade::isAtendeEtereos);
        if (!atendeEtereos) {
            throw new RegraEspecieException(
                "Fantasmas so podem ser atendidos por medicos com especializacao em entidades etereas.");
        }
    }

    @Override
    public String especie() { return "Fantasma"; }
}

package com.medmonstros.entities;

import com.medmonstros.exceptions.RegraEspecieException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("VAMPIRO")
@Getter @Setter @NoArgsConstructor
public class Vampiro extends Paciente {

    private boolean toleranciaSolar = false;

    public Vampiro(String nome, String email, String senhaHash, boolean toleranciaSolar) {
        super(nome, email, senhaHash);
        this.toleranciaSolar = toleranciaSolar;
    }

    @Override
    public void validarAgendamento(HorarioDisponivel horario, Medico medico) {
        if (!horario.eNoturno()) {
            throw new RegraEspecieException("Vampiros so podem agendar consultas em horarios noturnos.");
        }
    }

    @Override
    public String especie() { return "Vampiro"; }
}

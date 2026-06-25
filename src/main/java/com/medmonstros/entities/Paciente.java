package com.medmonstros.entities;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public abstract class Paciente extends Usuario {

    protected Paciente(String nome, String email, String senhaHash) {
        super(nome, email, senhaHash);
    }

    public abstract void validarAgendamento(HorarioDisponivel horario, Medico medico);

    public abstract String especie();

    @Override
    public String tipoUsuario() { return "PACIENTE"; }
}

package com.medmonstros.entities;

import com.medmonstros.enums.StatusConsulta;
import com.medmonstros.exceptions.RegraNegocioException;
import com.medmonstros.exceptions.TransicaoConsultaInvalidaException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "consulta")
@Getter @NoArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @OneToOne
    @JoinColumn(name = "horario_id")
    private HorarioDisponivel horario;

    @Enumerated(EnumType.STRING)
    private StatusConsulta status = StatusConsulta.SOLICITADA;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tratamento_id")
    private Tratamento tratamento;

    private LocalDateTime criadaEm = LocalDateTime.now();

    private static final Map<StatusConsulta, Set<StatusConsulta>> TRANSICOES = Map.of(
        StatusConsulta.SOLICITADA, EnumSet.of(StatusConsulta.ACEITA, StatusConsulta.CANCELADA, StatusConsulta.RECUSADA),
        StatusConsulta.ACEITA,     EnumSet.of(StatusConsulta.REALIZADA, StatusConsulta.CANCELADA)
    );

    public Consulta(Paciente paciente, Medico medico, HorarioDisponivel horario) {
        this.paciente = paciente;
        this.medico = medico;
        this.horario = horario;
    }

    public void aceitar()  { transicionar(StatusConsulta.ACEITA); }    // RN04
    public void recusar()  { transicionar(StatusConsulta.RECUSADA); }
    public void cancelar() { transicionar(StatusConsulta.CANCELADA); } // RN06
    public void realizar() { transicionar(StatusConsulta.REALIZADA); } // RN05

    private void transicionar(StatusConsulta novo) {
        if (!TRANSICOES.getOrDefault(status, Set.of()).contains(novo)) {
            throw new TransicaoConsultaInvalidaException(status, novo);
        }
        this.status = novo;
    }

    public void registrarTratamento(Tratamento t) {
        if (status != StatusConsulta.REALIZADA) {
            throw new RegraNegocioException("Tratamento só pode ser registrado após a realização da consulta.");
        }
        this.tratamento = t;
    }

    @Override
    public String toString() {
        return "Consulta{id=" + id + ", status=" + status
                + ", paciente=" + (paciente != null ? paciente.getNome() : null)
                + ", medico=" + (medico != null ? medico.getNome() : null) + "}";
    }
}

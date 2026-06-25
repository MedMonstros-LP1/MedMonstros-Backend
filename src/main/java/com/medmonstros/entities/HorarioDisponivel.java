package com.medmonstros.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "horario_disponivel")
@Getter @Setter @NoArgsConstructor
public class HorarioDisponivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    private LocalDateTime inicio;
    private LocalDateTime fim;

    private boolean luaCheia = false;

    private boolean ocupado = false;

    public HorarioDisponivel(Medico medico, LocalDateTime inicio, LocalDateTime fim, boolean luaCheia) {
        this.medico = medico;
        this.inicio = inicio;
        this.fim = fim;
        this.luaCheia = luaCheia;
    }

    public boolean eNoturno() {
        int h = inicio.getHour();
        return h >= 18 || h < 6;
    }
}

package com.medmonstros.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("MEDICO")
@Getter @Setter @NoArgsConstructor
public class Medico extends Usuario {

    @Column(unique = true)
    private String registroConselho;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "medico_especialidade",
        joinColumns = @JoinColumn(name = "medico_id"),
        inverseJoinColumns = @JoinColumn(name = "especialidade_id"))
    private Set<Especialidade> especialidades = new HashSet<>();

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioDisponivel> horarios = new ArrayList<>();

    public Medico(String nome, String email, String senhaHash, String registroConselho) {
        super(nome, email, senhaHash);
        this.registroConselho = registroConselho;
    }

    public void adicionarEspecialidade(Especialidade e) { especialidades.add(e); }
    public void removerEspecialidade(Especialidade e) { especialidades.remove(e); }

    @Override
    public String tipoUsuario() { return "MEDICO"; }
}

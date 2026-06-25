package com.medmonstros.repositories;

import com.medmonstros.entities.HorarioDisponivel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HorarioDisponivelRepository extends JpaRepository<HorarioDisponivel, Long> {
    List<HorarioDisponivel> findByMedicoIdAndOcupadoFalse(Long medicoId);
}

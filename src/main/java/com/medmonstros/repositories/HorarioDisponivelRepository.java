package com.medmonstros.repositories;

import com.medmonstros.entities.HorarioDisponivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HorarioDisponivelRepository extends JpaRepository<HorarioDisponivel, Long> {
    List<HorarioDisponivel> findByMedicoIdAndOcupadoFalse(Long medicoId);

    List<HorarioDisponivel> findByMedicoIdAndOcupadoFalseOrderByInicioAsc(Long medicoId);

    List<HorarioDisponivel> findByMedicoIdOrderByInicioAsc(Long medicoId);

    @Query("SELECT COUNT(h) > 0 FROM HorarioDisponivel h " +
           "WHERE h.medico.id = :medicoId " +
           "AND h.inicio < :fim AND h.fim > :inicio")
    boolean existsSobreposicao(@Param("medicoId") Long medicoId,
                               @Param("inicio") LocalDateTime inicio,
                               @Param("fim") LocalDateTime fim);
}

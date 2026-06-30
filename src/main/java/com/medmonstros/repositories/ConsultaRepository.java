package com.medmonstros.repositories;

import com.medmonstros.entities.Consulta;
import com.medmonstros.entities.Medico;
import com.medmonstros.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    
    List<Consulta> findByPaciente(Paciente paciente);
    
    List<Consulta> findByMedico(Medico medico);
}

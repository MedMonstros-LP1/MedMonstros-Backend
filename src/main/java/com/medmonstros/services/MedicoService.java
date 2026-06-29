package com.medmonstros.services;

import com.medmonstros.dtos.MedicoDetalheDTO;
import com.medmonstros.dtos.MedicoResumoDTO;
import com.medmonstros.entities.Medico;
import com.medmonstros.entities.Especialidade;
import com.medmonstros.repositories.MedicoRepository;
import com.medmonstros.repositories.EspecialidadeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final EspecialidadeRepository especialidadeRepository; 

    public MedicoService(MedicoRepository medicoRepository, EspecialidadeRepository especialidadeRepository) {
        this.medicoRepository = medicoRepository;
        this.especialidadeRepository = especialidadeRepository;
    }

    public List<MedicoResumoDTO> listarTodos() {
        return medicoRepository.findAll().stream()
                .map(MedicoResumoDTO::new)
                .collect(Collectors.toList());
    }

    public MedicoDetalheDTO buscarDetalhesPorId(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado."));
        return new MedicoDetalheDTO(medico);
    }

    @Transactional
    public void associarEspecialidade(Long medicoId, Long espId) {
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado."));
        
        Especialidade esp = especialidadeRepository.findById(espId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidade não encontrada."));

        if (!medico.getEspecialidades().contains(esp)) {
            medico.getEspecialidades().add(esp);
            medicoRepository.save(medico);
        }
    }

    @Transactional
    public void desassociarEspecialidade(Long medicoId, Long espId) {
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado."));
        
        Especialidade esp = especialidadeRepository.findById(espId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidade não encontrada."));

        if (!medico.getEspecialidades().contains(esp)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Especialidade não está associada a este médico.");
        }

        medico.getEspecialidades().remove(esp);
        medicoRepository.save(medico);
    }
}
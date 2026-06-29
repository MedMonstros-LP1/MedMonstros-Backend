package com.medmonstros.services;

import com.medmonstros.dtos.MedicoDetalheDTO;
import com.medmonstros.dtos.MedicoResumoDTO;
import com.medmonstros.entities.Medico;
import com.medmonstros.repositories.MedicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public List<MedicoResumoDTO> listarTodos() {
        return medicoRepository.findAll().stream()
                .map(MedicoResumoDTO::new)
                .collect(Collectors.toList());
    }

    public MedicoDetalheDTO buscarDetalhesPorId(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado."));
        return new MedicoDetalheDTO(medico);
    }
}
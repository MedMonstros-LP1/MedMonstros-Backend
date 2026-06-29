package com.medmonstros.services;

import com.medmonstros.dtos.EspecialidadeRequestDTO;
import com.medmonstros.dtos.EspecialidadeResponseDTO;
import com.medmonstros.entities.Especialidade;
import com.medmonstros.repositories.EspecialidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspecialidadeService {

    private final EspecialidadeRepository repository;

    public EspecialidadeService(EspecialidadeRepository repository) {
        this.repository = repository;
    }

    public List<EspecialidadeResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(EspecialidadeResponseDTO::new)
                .collect(Collectors.toList());
    }

    public EspecialidadeResponseDTO buscarPorId(Long id) {
        Especialidade especialidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada com o ID: " + id)); 
        return new EspecialidadeResponseDTO(especialidade);
    }

    @Transactional
    public EspecialidadeResponseDTO criar(EspecialidadeRequestDTO dto) {
        Especialidade especialidade = new Especialidade();
        especialidade.setNome(dto.nome());
        especialidade.setAtendeEtereos(dto.atendeEtereos());
        
        especialidade = repository.save(especialidade);
        return new EspecialidadeResponseDTO(especialidade);
    }

    @Transactional
    public EspecialidadeResponseDTO atualizar(Long id, EspecialidadeRequestDTO dto) {
        Especialidade especialidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada."));
                
        especialidade.setNome(dto.nome());
        especialidade.setAtendeEtereos(dto.atendeEtereos());
        
        especialidade = repository.save(especialidade);
        return new EspecialidadeResponseDTO(especialidade);
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Especialidade não encontrada.");
        }
        repository.deleteById(id);
    }
}
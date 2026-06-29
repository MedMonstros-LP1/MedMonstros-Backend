package com.medmonstros.services;

import com.medmonstros.dtos.EspecialidadeRequestDTO;
import com.medmonstros.dtos.EspecialidadeResponseDTO;
import com.medmonstros.entities.Especialidade;
import com.medmonstros.repositories.EspecialidadeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidade não encontrada com o ID: " + id)); 
        return new EspecialidadeResponseDTO(especialidade);
    }

    @Transactional
    public EspecialidadeResponseDTO criar(EspecialidadeRequestDTO dto) {
        if (repository.existsByNome(dto.nome())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Nome de especialidade já existe.");
        }

        Especialidade especialidade = new Especialidade();
        especialidade.setNome(dto.nome());
        especialidade.setAtendeEtereos(dto.atendeEtereos());
        
        especialidade = repository.save(especialidade);
        return new EspecialidadeResponseDTO(especialidade);
    }

    @Transactional
    public EspecialidadeResponseDTO atualizar(Long id, EspecialidadeRequestDTO dto) {
        Especialidade especialidade = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidade não encontrada."));
                
        if (repository.existsByNome(dto.nome()) && !especialidade.getNome().equals(dto.nome())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Nome de especialidade já existe.");
        }

        especialidade.setNome(dto.nome());
        especialidade.setAtendeEtereos(dto.atendeEtereos());
        
        especialidade = repository.save(especialidade);
        return new EspecialidadeResponseDTO(especialidade);
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidade não encontrada.");
        }
        try {
            repository.deleteById(id);
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Não é possível deletar uma especialidade associada a médicos.");
        }
    }
}
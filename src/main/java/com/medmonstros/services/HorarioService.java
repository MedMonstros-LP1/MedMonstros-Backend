package com.medmonstros.services;

import com.medmonstros.dtos.HorarioRequestDTO;
import com.medmonstros.dtos.HorarioResponseDTO;
import com.medmonstros.entities.HorarioDisponivel;
import com.medmonstros.entities.Medico;
import com.medmonstros.exceptions.RecursoNaoEncontradoException;
import com.medmonstros.exceptions.RegraNegocioException;
import com.medmonstros.repositories.HorarioDisponivelRepository;
import com.medmonstros.repositories.MedicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HorarioService {

    private final HorarioDisponivelRepository horarioRepository;
    private final MedicoRepository medicoRepository;

    public HorarioService(HorarioDisponivelRepository horarioRepository, MedicoRepository medicoRepository) {
        this.horarioRepository = horarioRepository;
        this.medicoRepository = medicoRepository;
    }

    @Transactional
    public HorarioResponseDTO criar(HorarioRequestDTO dto) {
        if (!dto.inicio().isBefore(dto.fim())) {
            throw new RegraNegocioException("O horário de início deve ser anterior ao horário de fim");
        }

        Medico medico = medicoRepository.findById(dto.medicoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Médico não encontrado"));

        boolean conflito = horarioRepository.existsSobreposicao(dto.medicoId(), dto.inicio(), dto.fim());
        if (conflito) {
            throw new RegraNegocioException("Já existe um horário cadastrado que conflita com o período informado");
        }

        HorarioDisponivel horario = new HorarioDisponivel(medico, dto.inicio(), dto.fim(), dto.luaCheia());
        horario.setOcupado(false);
        horario = horarioRepository.save(horario);

        return new HorarioResponseDTO(horario);
    }

    public List<HorarioResponseDTO> listarLivres(Long medicoId) {
        return horarioRepository.findByMedicoIdAndOcupadoFalseOrderByInicioAsc(medicoId).stream()
                .map(HorarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<HorarioResponseDTO> listarTodos(Long medicoId) {
        return horarioRepository.findByMedicoIdOrderByInicioAsc(medicoId).stream()
                .map(HorarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletar(Long id) {
        HorarioDisponivel horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Horário não encontrado"));

        if (horario.isOcupado()) {
            throw new RegraNegocioException("Não é possível remover um horário que já está ocupado por uma consulta");
        }

        horarioRepository.delete(horario);
    }
}

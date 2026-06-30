package com.medmonstros.services;

import com.medmonstros.dtos.RegistroPacienteDTO;
import com.medmonstros.entities.Fantasma;
import com.medmonstros.entities.Lobisomem;
import com.medmonstros.entities.Paciente;
import com.medmonstros.entities.Vampiro;
import com.medmonstros.exceptions.RecursoNaoEncontradoException;
import com.medmonstros.exceptions.RegraNegocioException;
import com.medmonstros.repositories.PacienteRepository;
import com.medmonstros.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    private final UsuarioRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;

    public PacienteService(UsuarioRepository usuarioRepository,
                           PacienteRepository pacienteRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.pacienteRepository = pacienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Paciente registrar(RegistroPacienteDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new RegraNegocioException("Ja existe um usuario com o e-mail " + dto.email());
        }
        String hash = passwordEncoder.encode(dto.senha());
        Paciente paciente = switch (dto.especie()) {
            case VAMPIRO -> new Vampiro(dto.nome(), dto.email(), hash,
                    Boolean.TRUE.equals(dto.toleranciaSolar()));
            case LOBISOMEM -> new Lobisomem(dto.nome(), dto.email(), hash,
                    Boolean.TRUE.equals(dto.controlaTransformacao()));
            case FANTASMA -> new Fantasma(dto.nome(), dto.email(), hash,
                    dto.anosAssombrando() != null ? dto.anosAssombrando() : 0);
        };
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Paciente buscarPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente nao encontrado com id: " + id));
    }
}

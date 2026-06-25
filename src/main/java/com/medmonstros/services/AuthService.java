package com.medmonstros.services;

import com.medmonstros.dtos.LoginDTO;
import com.medmonstros.dtos.RegistroMedicoDTO;
import com.medmonstros.entities.Medico;
import com.medmonstros.entities.Usuario;
import com.medmonstros.exceptions.CredenciaisInvalidasException;
import com.medmonstros.exceptions.RegraNegocioException;
import com.medmonstros.repositories.MedicoRepository;
import com.medmonstros.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final MedicoRepository medicoRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository,
                       MedicoRepository medicoRepository,
                       PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.medicoRepository = medicoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Medico registrarMedico(RegistroMedicoDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new RegraNegocioException("Ja existe um usuario com o e-mail " + dto.email());
        }
        String hash = passwordEncoder.encode(dto.senha());
        Medico medico = new Medico(dto.nome(), dto.email(), hash, dto.registroConselho());
        return medicoRepository.save(medico);
    }

    public Usuario login(LoginDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new CredenciaisInvalidasException("E-mail ou senha invalidos."));
        if (!passwordEncoder.matches(dto.senha(), usuario.getSenhaHash())) {
            throw new CredenciaisInvalidasException("E-mail ou senha invalidos.");
        }
        return usuario;
    }
}

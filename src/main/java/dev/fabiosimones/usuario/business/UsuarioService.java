package dev.fabiosimones.usuario.business;

import dev.fabiosimones.usuario.business.converter.UsuarioConverter;
import dev.fabiosimones.usuario.business.dto.UsuarioDTO;
import dev.fabiosimones.usuario.infrastructure.entity.Usuario;
import dev.fabiosimones.usuario.infrastructure.exceptions.ConflictException;
import dev.fabiosimones.usuario.infrastructure.exceptions.ResourceNotFoundException;
import dev.fabiosimones.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(bCryptPasswordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario)
        );
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void emailExiste(String email){
        try {
            boolean existe = verificaEmailExistente(email);
            if(existe)
                throw new ConflictException("E-mail já cadastrado: " + email);
        }catch (ConflictException e){
            throw new ConflictException("E-mail já cadastrado: " + email);
        }
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado " + email)
        );
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }
}

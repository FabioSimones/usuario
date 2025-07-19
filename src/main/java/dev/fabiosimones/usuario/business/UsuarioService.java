package dev.fabiosimones.usuario.business;

import dev.fabiosimones.usuario.business.converter.UsuarioConverter;
import dev.fabiosimones.usuario.business.dto.UsuarioDTO;
import dev.fabiosimones.usuario.infrastructure.entity.Usuario;
import dev.fabiosimones.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario)
        );
    }
}

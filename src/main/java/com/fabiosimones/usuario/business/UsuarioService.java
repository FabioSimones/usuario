package com.fabiosimones.usuario.business;

import com.fabiosimones.usuario.business.converter.UsuarioConverter;
import com.fabiosimones.usuario.business.dto.UsuarioDTO;
import com.fabiosimones.usuario.infrastructure.entity.Usuario;
import com.fabiosimones.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}

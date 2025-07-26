package dev.fabiosimones.usuario.business;

import dev.fabiosimones.usuario.business.converter.UsuarioConverter;
import dev.fabiosimones.usuario.business.dto.EnderecoDTO;
import dev.fabiosimones.usuario.business.dto.TelefoneDTO;
import dev.fabiosimones.usuario.business.dto.UsuarioDTO;
import dev.fabiosimones.usuario.infrastructure.entity.Endereco;
import dev.fabiosimones.usuario.infrastructure.entity.Telefone;
import dev.fabiosimones.usuario.infrastructure.entity.Usuario;
import dev.fabiosimones.usuario.infrastructure.exceptions.ConflictException;
import dev.fabiosimones.usuario.infrastructure.exceptions.ResourceNotFoundException;
import dev.fabiosimones.usuario.infrastructure.repository.EnderecoRepository;
import dev.fabiosimones.usuario.infrastructure.repository.TelefoneRepository;
import dev.fabiosimones.usuario.infrastructure.repository.UsuarioRepository;
import dev.fabiosimones.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

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

    public UsuarioDTO buscarUsuarioPorEmail(String email){
        try {
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(() ->
                    new ResourceNotFoundException("Email não encontrado " + email)
            ));
        }
        catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Email não encontrado." + email);
        }

    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO){
        //Busca email do usuário para tirar obrigatoriedade do email.
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        //Criptografia de senha.
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? bCryptPasswordEncoder.encode(
                usuarioDTO.getSenha()) : null);

        //Busca dados do usuário no banco de dados.
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado."));

        //Mesclou dados que recebemos na requisição DTO com os dados do banco de dados.
        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO, usuarioEntity);

        //Salvado dados do usuário, pegou o retorno e converteu para UsuarioDTO.
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado" + idEndereco));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado." + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, entity);

    }
}

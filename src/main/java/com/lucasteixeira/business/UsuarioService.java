package com.lucasteixeira.business;


import com.lucasteixeira.business.converter.UsuarioConverter;
import com.lucasteixeira.business.dto.UsuarioDTO;
import com.lucasteixeira.infrastructure.entity.Usuario;
import com.lucasteixeira.infrastructure.exceptions.ConflictException;
import com.lucasteixeira.infrastructure.exceptions.ResourceNotFoundException;
import com.lucasteixeira.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new ConflictException("Email já cadastrado" + email);
            }
        }catch (ConflictException e ){
            throw new ConflictException("Email já cadrastado" + e.getCause());
        }
    }

    //Ele chama o método la na repository
    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }


    public Usuario buscUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email));
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }
}

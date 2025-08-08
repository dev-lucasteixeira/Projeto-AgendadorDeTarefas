package com.lucasteixeira.business;


import com.lucasteixeira.business.converter.UsuarioConverter;
import com.lucasteixeira.business.dto.UsuarioDTO;
import com.lucasteixeira.com.lucasteixeira.infrastructure.entity.Usuario;
import com.lucasteixeira.com.lucasteixeira.infrastructure.repository.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }
}

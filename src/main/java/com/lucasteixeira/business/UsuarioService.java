package com.lucasteixeira.business;


import com.lucasteixeira.business.converter.UsuarioConverter;
import com.lucasteixeira.business.dto.UsuarioDTO;
import com.lucasteixeira.infrastructure.entity.Usuario;
import com.lucasteixeira.infrastructure.exceptions.ConflictException;
import com.lucasteixeira.infrastructure.exceptions.ResourceNotFoundException;
import com.lucasteixeira.infrastructure.repository.UsuarioRepository;
import com.lucasteixeira.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


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

    public UsuarioDTO atualizaUsuario(String token, UsuarioDTO dto){
        //busca o usuario pelo token para tirar a obrigatoriaedade do email
        String email = jwtUtil.extractEmailToken((token.substring(7)));

        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        //busca os dados usuario no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow( () ->
                new ResourceNotFoundException("Email não encontrado"));

        //mesclou os dados que recebemos na requisição com os dados do banco de dados
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);


        //salvou os dados do usuario convertidos e depois pegoui o retorno e converteu para usuarioDTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}

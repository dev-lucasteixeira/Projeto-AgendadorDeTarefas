package com.lucasteixeira.business.converter;

import com.lucasteixeira.business.dto.EnderecoDTO;
import com.lucasteixeira.business.dto.TelefoneDTO;
import com.lucasteixeira.business.dto.UsuarioDTO;
import com.lucasteixeira.infrastructure.entity.Endereco;
import com.lucasteixeira.infrastructure.entity.Telefone;
import com.lucasteixeira.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component //ela é um compenente a mais na aplicação, mas sem regras de negócios
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefones(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecosDTO){
        return enderecosDTO.stream().map(this::paraEndereco).toList(); //Ela mapeia a lista e converte tudo, ai formaa outra lista no lugar com os valores que queremos
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .build();
    }

    public List<Telefone> paraListaTelefones(List<TelefoneDTO> telefonesDTO){
        return telefonesDTO.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }





    public UsuarioDTO paraUsuarioDTO(Usuario usuarioDTO){
        return UsuarioDTO.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEnderecoDTO(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuarioDTO.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List <Endereco> enderecosDTOS){
        return enderecosDTOS.stream().map(this::paraEnderecoDTO).toList(); //Ela mapeia a lista e converte tudo, ai formaa outra lista no lugar com os valores que queremos
    }

    public EnderecoDTO paraEnderecoDTO(Endereco enderecoDTO){
        return EnderecoDTO.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefonesDTOS){
        return telefonesDTOS.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefoneDTO){
        return TelefoneDTO.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }
}

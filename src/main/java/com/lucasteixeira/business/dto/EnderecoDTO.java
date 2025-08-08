package com.lucasteixeira.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTO {
    private String rua;
    private long numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;
}

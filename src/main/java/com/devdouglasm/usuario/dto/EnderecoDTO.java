package com.devdouglasm.usuario.dto;

import com.devdouglasm.usuario.infrastructure.entity.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EnderecoDTO {

    private Long id;
    private String rua;
    private Long numero;
    private String complemento;
    private String cep;


    public EnderecoDTO(Endereco entity) {
        id = entity.getId();
        rua = entity.getRua();
        numero = entity.getNumero();
        complemento = entity.getComplemento();
        cep = entity.getCep();
    }

}

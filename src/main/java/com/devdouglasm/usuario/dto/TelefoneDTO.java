package com.devdouglasm.usuario.dto;

import com.devdouglasm.usuario.infrastructure.entity.Telefone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TelefoneDTO {

    private Long id;
    private String numero;
    private String ddd;

    public TelefoneDTO(Telefone entity) {
        id = entity.getId();
        numero = entity.getNumero();
        ddd = entity.getDdd();
    }
}

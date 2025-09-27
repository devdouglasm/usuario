package com.devdouglasm.usuario.infrastructure.entity;

import com.devdouglasm.usuario.dto.TelefoneDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "telefone")
@Entity
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", length = 10)
    private String numero;
    @Column(name = "ddd", length = 3)
    private String ddd;

    public Telefone(TelefoneDTO dto) {
        id = dto.getId();
        numero = dto.getNumero();
        ddd = dto.getDdd();
    }
}

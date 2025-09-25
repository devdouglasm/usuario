package com.devdouglasm.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "endereco")
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rua", length = 200)
    private String rua;
    @Column(name = "numero")
    private Long numero;
    @Column(name = "complemento", length = 20)
    private String complemento;
    @Column(name = "cep", length = 9)
    private String cep;
}

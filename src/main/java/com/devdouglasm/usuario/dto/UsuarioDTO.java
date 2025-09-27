package com.devdouglasm.usuario.dto;

import com.devdouglasm.usuario.infrastructure.entity.Endereco;
import com.devdouglasm.usuario.infrastructure.entity.Telefone;
import com.devdouglasm.usuario.infrastructure.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;

    private final List<EnderecoDTO> enderecos = new ArrayList<>();
    private final List<TelefoneDTO> telefones = new ArrayList<>();

    public UsuarioDTO(Usuario usuario) {
        id = usuario.getId();
        nome = usuario.getNome();
        email = usuario.getEmail();
        senha = usuario.getSenha();
        for (Endereco endereco : usuario.getEnderecos()) {
            EnderecoDTO enderecoDTO = new EnderecoDTO(endereco);
            addEndereco(enderecoDTO);
        }
        for (Telefone telefone : usuario.getTelefones()) {
            TelefoneDTO telefoneDTO = new TelefoneDTO(telefone);
            addTelefone(telefoneDTO);
        }
    }

    public void addTelefone(TelefoneDTO telefoneDTO) {
        telefones.add(telefoneDTO);
    }

    public void addEndereco(EnderecoDTO enderecoDTO) {
        enderecos.add(enderecoDTO);
    }
}

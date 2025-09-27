package com.devdouglasm.usuario.business;

import com.devdouglasm.usuario.dto.UsuarioDTO;
import com.devdouglasm.usuario.infrastructure.entity.Endereco;
import com.devdouglasm.usuario.infrastructure.entity.Telefone;
import com.devdouglasm.usuario.infrastructure.entity.Usuario;
import com.devdouglasm.usuario.infrastructure.exceptions.ConflictException;
import com.devdouglasm.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.devdouglasm.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioDTO salvaUsuario(UsuarioDTO dto) {
        emailExiste(dto.getEmail());
        Usuario usuario = new Usuario();
        copyDtoToEntity(dto, usuario);
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuarioRepository.save(usuario);
        return new UsuarioDTO(usuario);
    }

    public Usuario achaUsuario(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email));
    }

    @Transactional
    public void deletarUsuario(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    private void copyDtoToEntity(UsuarioDTO dto, Usuario entity) {
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setSenha(dto.getSenha());
        entity.setEnderecos(dto.getEnderecos().stream().map(Endereco::new).toList());
        entity.setTelefones(dto.getTelefones().stream().map(Telefone::new).toList());
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email ja cadastrado " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado " + e.getCause());
        }

    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

}

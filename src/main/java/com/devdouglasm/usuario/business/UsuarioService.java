package com.devdouglasm.usuario.business;

import com.devdouglasm.usuario.dto.EnderecoDTO;
import com.devdouglasm.usuario.dto.TelefoneDTO;
import com.devdouglasm.usuario.dto.UsuarioDTO;
import com.devdouglasm.usuario.infrastructure.entity.Endereco;
import com.devdouglasm.usuario.infrastructure.entity.Telefone;
import com.devdouglasm.usuario.infrastructure.entity.Usuario;
import com.devdouglasm.usuario.infrastructure.exceptions.ConflictException;
import com.devdouglasm.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.devdouglasm.usuario.infrastructure.repository.EnderecoRepository;
import com.devdouglasm.usuario.infrastructure.repository.TelefoneRepository;
import com.devdouglasm.usuario.infrastructure.repository.UsuarioRepository;
import com.devdouglasm.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    @Transactional
    public UsuarioDTO salvaUsuario(UsuarioDTO dto) {
        emailExiste(dto.getEmail());
        Usuario usuario = new Usuario();
        copyDtoToEntity(dto, usuario);
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuarioRepository.save(usuario);
        return new UsuarioDTO(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioDTO achaUsuario(String email) {
        try {
            return new UsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado " + email)));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }

    @Transactional
    public void deletarUsuario(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    @Transactional
    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado"));
        updateUsuario(dto, usuario);
        return new UsuarioDTO(usuario);
    }

    @Transactional
    public EnderecoDTO atualizaDadosEndereco(Long id, EnderecoDTO dto) {
        Endereco entity = enderecoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado " + id)
        );
        updateEndereco(dto, entity);
        return new EnderecoDTO(enderecoRepository.save(entity));
    }

    @Transactional
    public TelefoneDTO atualizaDadosTelefone(Long id, TelefoneDTO dto) {
        Telefone entity = telefoneRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado " + id)
        );
        updateTelefone(dto, entity);
        return new TelefoneDTO(telefoneRepository.save(entity));
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

    private void updateUsuario(UsuarioDTO dto, Usuario entity) {
        entity.setNome(dto.getNome() != null ? dto.getNome() : entity.getNome());
        entity.setEmail(dto.getEmail() != null ? dto.getEmail() : entity.getEmail());
        entity.setId(entity.getId());
        entity.setSenha(dto.getSenha() != null ? dto.getSenha() : entity.getSenha());
        entity.setEnderecos(entity.getEnderecos());
        entity.setTelefones(entity.getTelefones());
    }

    private void updateEndereco(EnderecoDTO dto, Endereco entity) {
        entity.setId(entity.getId());
        entity.setRua(dto.getRua() != null ? dto.getRua() : entity.getRua());
        entity.setNumero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero());
        entity.setCep(dto.getCep() != null ? dto.getCep() : entity.getCep());
        entity.setComplemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento());
    }

    private void updateTelefone(TelefoneDTO dto, Telefone entity) {
        entity.setId(entity.getId());
        entity.setNumero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero());
        entity.setDdd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd());
    }
}

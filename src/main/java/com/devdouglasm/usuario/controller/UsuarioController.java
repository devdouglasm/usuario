package com.devdouglasm.usuario.controller;

import com.devdouglasm.usuario.business.UsuarioService;
import com.devdouglasm.usuario.dto.EnderecoDTO;
import com.devdouglasm.usuario.dto.TelefoneDTO;
import com.devdouglasm.usuario.dto.UsuarioDTO;
import com.devdouglasm.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.salvaUsuario(dto));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),
                        usuarioDTO.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(usuarioDTO.getEmail());
    }

    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTO> cadastraEndereco(@RequestBody EnderecoDTO dto,
                                                        @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.cadastraEndereco(token, dto));
    }

    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO> cadastraTelefone(@RequestBody TelefoneDTO dto,
                                                        @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.cadastraTelefone(token, dto));
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> achaUsuario(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.achaUsuario(email));
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizaDadosUsuario(@RequestBody UsuarioDTO dto,
                                                           @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto));
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizaDadosEndereco(@RequestBody EnderecoDTO dto,
                                                             @RequestParam("id") Long id) {
        return ResponseEntity.ok(usuarioService.atualizaDadosEndereco(id, dto));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizaDadosTelefone(@RequestBody TelefoneDTO dto,
                                                             @RequestParam("id") Long id) {
        return ResponseEntity.ok(usuarioService.atualizaDadosTelefone(id, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarUsuario(@RequestParam String email) {
        usuarioService.deletarUsuario(email);
        return ResponseEntity.noContent().build();
    }
}

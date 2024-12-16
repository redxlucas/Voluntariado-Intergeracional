package com.db.desafio_voluntariado.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_voluntariado.entities.Idoso;
import com.db.desafio_voluntariado.entities.Usuario;
import com.db.desafio_voluntariado.infra.security.TokenService;
import com.db.desafio_voluntariado.repository.IdosoRepository;
import com.db.desafio_voluntariado.repository.UsuarioRepository;
import com.db.desafio_voluntariado.services.IdosoService;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private IdosoRepository idosoRepository;

    private IdosoService idosoService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        Usuario usuario = usuarioRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), usuario.getSenha())) {
            String token = this.tokenService.generateToken(usuario);
            return ResponseEntity.ok(new ResponseDTO(usuario.getNomeCompleto(), token));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register/idoso")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<Idoso> idosoOptional = idosoRepository.findByEmail(body.email());

        if(idosoOptional.isEmpty()) {
            idosoService.adicionarIdoso(idosoOptional.get());
        }
        String token = this.tokenService.generateToken(idosoOptional);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<Usuario> user = usuarioRepository.findByEmail(body.email());

        if(user.isEmpty()) {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setPassword(passwordEncoder.encode(body.password()));
            novoUsuario.setEmail(body.email());
            novoUsuario.setName(body.name());
            usuarioRepository.save(novoUsuario);

            String token = this.tokenService.generateToken(novoUsuario);
            return ResponseEntity.ok(new ResponseDTO(novoUsuario.getNomeCompleto(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
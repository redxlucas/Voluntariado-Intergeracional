package com.db.desafio_voluntariado.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.entities.Usuario;
import com.db.desafio_voluntariado.services.UsuarioService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/usuario")
    public ResponseEntity<Usuario> add (Usuario usuario){
        Usuario novoUsuario = usuarioService.add(usuario);
        return ResponseEntity.status(201).body(novoUsuario);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<UsuarioDTO> getOneUsuarioById(@PathVariable Integer id) {
        UsuarioDTO usuarioDTO = usuarioService.getOne(id);
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping("/usuario/interesse/{id}")
    public ResponseEntity<UsuarioDTO> getAreaDeInteresse(@PathVariable Integer id) {
        UsuarioDTO usuarioDTO = usuarioService.getOne(id);
        return ResponseEntity.ok(usuarioDTO);
    }
    
    @GetMapping("/usuario/todos")
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarioById() {
        List<UsuarioDTO> usuarioDTOList = usuarioService.getAll();
        return ResponseEntity.ok(usuarioDTOList);
    }

}


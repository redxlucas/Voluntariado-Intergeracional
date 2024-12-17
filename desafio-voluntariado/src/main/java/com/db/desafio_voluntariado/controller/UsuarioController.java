package com.db.desafio_voluntariado.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.services.UsuarioService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getOneUsuarioById(@PathVariable Integer id) {
        UsuarioDTO usuarioDTO = usuarioService.getOne(id);
        return ResponseEntity.ok(usuarioDTO);
    }
    
    @GetMapping("/todos")
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarioById() {
        List<UsuarioDTO> usuarioDTOList = usuarioService.getAll();
        return ResponseEntity.ok(usuarioDTOList);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<Page<UsuarioDTO>> filtrarUsuarios(
        @RequestParam(required = false) String email,
        @RequestParam(required = false) List<Integer> atividadeDeInteresseList,
        @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        Page<UsuarioDTO> usuarios = usuarioService.filtrarUsuarios(email, atividadeDeInteresseList, pageable);
        return ResponseEntity.ok(usuarios);
    }
}


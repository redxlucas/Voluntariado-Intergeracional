package com.db.desafio_voluntariado.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_voluntariado.entities.Idoso;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.services.IdosoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/idoso")
public class IdosoController {
    @Autowired
    private IdosoService idosoService;

    @PostMapping
    public ResponseEntity<Idoso> adicionarIdoso(Idoso idoso) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(idosoService.adicionarIdoso(idoso));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getIdosoById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(idosoService.getOne(id));
    }

    @GetMapping("/todos")
    public List<UsuarioDTO> getAllIdosos() {
        return idosoService.getAll();
    }
}
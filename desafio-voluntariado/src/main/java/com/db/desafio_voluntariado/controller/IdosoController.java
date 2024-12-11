package com.db.desafio_voluntariado.controller;

import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_voluntariado.entities.Idoso;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.services.IdosoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "*")
public class IdosoController {

    @Autowired
    public IdosoService idosoService;

    @PostMapping("/idoso")
    public ResponseEntity<Idoso> add(@RequestBody Idoso idoso) {
        Idoso novoIdoso = idosoService.adicionar(idoso);
        return ResponseEntity.status(201).body(novoIdoso);
    }

    @GetMapping("/idoso/{id}")
    public ResponseEntity<UsuarioDTO> getOneById(@PathVariable Integer id) {
        UsuarioDTO idoso = idosoService.getOne(id);
        return ResponseEntity.ok(idoso);
    }

    @GetMapping("/idoso/todos")
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        List<UsuarioDTO> idosoList = (List<UsuarioDTO>) idosoService.getAll();
        return ResponseEntity.ok(idosoList);
    }
}

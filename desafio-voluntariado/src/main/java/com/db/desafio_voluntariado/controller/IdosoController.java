package com.db.desafio_voluntariado.controller;

import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_voluntariado.entidades.Idoso;
import com.db.desafio_voluntariado.entidades.IdosoDTO;
import com.db.desafio_voluntariado.services.IdosoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
// Colocar annotation para driblar erro de CORS
public class IdosoController {

    @Autowired
    public IdosoService idosoService;

    @PostMapping("/idoso")
    public ResponseEntity<Idoso> add(Idoso idoso) {
        Idoso novoIdoso = idosoService.adicionar(idoso);
        return ResponseEntity.status(201).body(novoIdoso);
    }

    @GetMapping("/idoso/{id}")
    public ResponseEntity<IdosoDTO> getOneById(@PathVariable Integer id) {
        IdosoDTO idoso = idosoService.getOne(id);
        return ResponseEntity.ok(idoso);
    }

    @GetMapping("/idoso/todos")
    public ResponseEntity<List<IdosoDTO>> getAll() {
        List<IdosoDTO> idosoList = (List<IdosoDTO>) idosoService.getAll();
        return ResponseEntity.ok(idosoList);
    }
}

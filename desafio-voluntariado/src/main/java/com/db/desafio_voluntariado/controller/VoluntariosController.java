package com.db.desafio_voluntariado.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_voluntariado.entidades.Voluntarios;
import com.db.desafio_voluntariado.entidades.VoluntariosDTO;
import com.db.desafio_voluntariado.services.VoluntariosService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class VoluntariosController {

    @Autowired
    public VoluntariosService voluntariosService;

    @PostMapping("/voluntario")
    public ResponseEntity<Voluntarios> add (Voluntarios voluntarios){

        Voluntarios novoVoluntario = voluntariosService.addVoluntarios(voluntarios);
        return ResponseEntity.status(201).body(novoVoluntario);
    }

    @GetMapping("/volunario/{id}")
    public ResponseEntity<VoluntariosDTO> getOneVoluntarioById(@PathVariable Integer id) {
        VoluntariosDTO voluntariosDTO = voluntariosService.getOne(id);
        return ResponseEntity.ok(voluntariosDTO);
    }
    
    @GetMapping("/voluntario/todos")
    public ResponseEntity<List<VoluntariosDTO>> getAllVoluntarioById() {
        List<VoluntariosDTO> voluntariosDTOList = voluntariosService.getAll();
        return ResponseEntity.ok(voluntariosDTOList);
    }

}


package com.db.desafio_voluntariado.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.entities.Voluntario;
import com.db.desafio_voluntariado.services.VoluntariosService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "*")
public class VoluntarioController {

    @Autowired
    public VoluntariosService voluntariosService;

    @PostMapping("/voluntario")
    public ResponseEntity<Voluntario> add (Voluntario voluntarios){

        Voluntario novoVoluntario = voluntariosService.addVoluntarios(voluntarios);
        return ResponseEntity.status(201).body(novoVoluntario);
    }

    @GetMapping("/volunario/{id}")
    public ResponseEntity<UsuarioDTO> getOneVoluntarioById(@PathVariable Integer id) {
        UsuarioDTO voluntariosDTO = voluntariosService.getOne(id);
        return ResponseEntity.ok(voluntariosDTO);
    }
    
    @GetMapping("/voluntario/todos")
    public ResponseEntity<List<UsuarioDTO>> getAllVoluntarioById() {
        List<UsuarioDTO> voluntariosDTOList = voluntariosService.getAll();
        return ResponseEntity.ok(voluntariosDTOList);
    }

}


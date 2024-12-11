package com.db.desafio_voluntariado.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.entities.Voluntario;
import com.db.desafio_voluntariado.services.VoluntariosService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "*")
public class VoluntarioController {

    @Autowired
    public VoluntariosService voluntariosService;

    @PostMapping("/voluntario")
    public ResponseEntity<Voluntario> add (@RequestBody Voluntario voluntario){

        Voluntario novoVoluntario = voluntariosService.addVoluntarios(voluntario);
        return ResponseEntity.status(201).body(novoVoluntario);
    }

    @GetMapping("/voluntario/{id}")
    public ResponseEntity<UsuarioDTO> getOneVoluntarioById(@PathVariable Integer id) {
        UsuarioDTO voluntarioDTO = voluntariosService.getOne(id);
        return ResponseEntity.ok(voluntarioDTO);
    }

    @GetMapping("/voluntario/interesse/{id}")
    public ResponseEntity<UsuarioDTO> getAreaDeInteresse(@PathVariable Integer id) {
        UsuarioDTO voluntarioDTO = voluntariosService.getOne(id);
        return ResponseEntity.ok(voluntarioDTO);
    }
    
    @GetMapping("/voluntario/todos")
    public ResponseEntity<List<UsuarioDTO>> getAllVoluntarioById() {
        List<UsuarioDTO> voluntarioDTOList = voluntariosService.getAll();
        return ResponseEntity.ok(voluntarioDTOList);
    }

}


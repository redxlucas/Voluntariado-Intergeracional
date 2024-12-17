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

import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.entities.Voluntario;
import com.db.desafio_voluntariado.services.VoluntarioService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/voluntario")
public class VoluntarioController {
    @Autowired
    private VoluntarioService voluntarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> adicionarVoluntario(Voluntario voluntario) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(voluntarioService.adicionarVoluntario(voluntario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getVoluntarioById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(voluntarioService.getOne(id));
    }

    @GetMapping("/todos")
    public List<UsuarioDTO> getAllVoluntarios() {
        return voluntarioService.getAll();
    }

    // @PostMapping("/{voluntarioId}/adicionar-pontos")
    // public ResponseEntity<PontosVoluntario> adicionarPontos(
    //         @PathVariable Long voluntarioId,
    //         @RequestBody int pontos) {
    //     return ResponseEntity.ok(voluntarioService.adicionarPontos(voluntarioId, pontos));
    // }

    // @GetMapping("/{voluntarioId}/pontos")
    // public ResponseEntity<PontosVoluntario> consultarPontos(
    //         @PathVariable Long voluntarioId) {
    //     return ResponseEntity.ok(voluntarioService.consultarPontos(voluntarioId));
    // }
}

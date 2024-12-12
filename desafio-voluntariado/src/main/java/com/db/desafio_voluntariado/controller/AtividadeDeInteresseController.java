package com.db.desafio_voluntariado.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_voluntariado.entities.AtividadeDeInteresse;
import com.db.desafio_voluntariado.services.AtividadeDeInteresseService;

@RestController
@CrossOrigin(origins = "*")
public class AtividadeDeInteresseController {
    
    @Autowired
    private AtividadeDeInteresseService atividadeDeInteresseService;

    @PostMapping("/atividadeDeInteresse")
    public ResponseEntity<AtividadeDeInteresse> add(AtividadeDeInteresse atividadeDeInteresse) {
        AtividadeDeInteresse atividadeDeInteresseSalva = atividadeDeInteresseService.add(atividadeDeInteresse);
        return ResponseEntity.status(201).body(atividadeDeInteresseSalva);
    }
}

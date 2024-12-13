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

import com.db.desafio_voluntariado.entities.Atividade;
import com.db.desafio_voluntariado.services.AtividadeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/atividade")
public class AtividadeController {
    @Autowired
    private AtividadeService atividadeService;

    @PostMapping
    public ResponseEntity<Atividade> criarAtividade(Atividade atividade) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(atividadeService.adicionarAtividade(atividade));
    }

    @GetMapping
    public ResponseEntity<List<Atividade>> listarAtividades() {
        return ResponseEntity.ok(atividadeService.getAll());
    }

    @PostMapping("/{atividadeId}/participantes")
    public ResponseEntity<Atividade> adicionarParticipante(
            @PathVariable Integer atividadeId,
            Integer idosoId, 
            Integer voluntarioId) {
        Atividade atividadeAtualizada = atividadeService.adicionarParticipante(atividadeId, idosoId, voluntarioId);
        return ResponseEntity.ok(atividadeAtualizada);
    }
}

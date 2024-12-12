package com.db.desafio_voluntariado.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_voluntariado.entities.AtividadeDeInteresse;
import com.db.desafio_voluntariado.entities.Usuario;
import com.db.desafio_voluntariado.services.AtividadeDeInteresseService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/atividade-interesse")
public class AtividadeInteresseController {
    @Autowired
    private AtividadeDeInteresseService atividadeDeInteresseService;

    @PostMapping
    public ResponseEntity<AtividadeDeInteresse> criarAtividadeInteresse(AtividadeDeInteresse atividadeInteresse) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(atividadeDeInteresseService.add(atividadeInteresse));
    }

    @GetMapping
    public ResponseEntity<List<AtividadeDeInteresse>> listarAtividades() {
        List<AtividadeDeInteresse> atividades = atividadeDeInteresseService.getAll();
        return ResponseEntity.ok(atividades);
    }
    
    @PostMapping("/vincular/{usuarioId}/{atividadeId}")
    public ResponseEntity<Usuario> vincularAtividade(
            @PathVariable Integer usuarioId, 
            @PathVariable Integer atividadeId) {
        Usuario usuario = atividadeDeInteresseService.vincularAtividadeAoUsuario(usuarioId, atividadeId);
        return ResponseEntity.ok(usuario);
    }

    // Desvincular uma atividade de interesse de um usuário
    @DeleteMapping("/desvincular/{usuarioId}/{atividadeId}")
    public ResponseEntity<Usuario> desvincularAtividade(
            @PathVariable Integer usuarioId, 
            @PathVariable Integer atividadeId) {
        Usuario usuario = atividadeDeInteresseService.desvincularAtividadeDoUsuario(usuarioId, atividadeId);
        return ResponseEntity.ok(usuario);
    }

    // Buscar atividades de interesse de um usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Set<AtividadeDeInteresse>> buscarAtividadesDoUsuario(
            @PathVariable Integer usuarioId) {
        Set<AtividadeDeInteresse> atividades = atividadeDeInteresseService.buscarAtividadesDoUsuario(usuarioId);
        return ResponseEntity.ok(atividades);
    }
}

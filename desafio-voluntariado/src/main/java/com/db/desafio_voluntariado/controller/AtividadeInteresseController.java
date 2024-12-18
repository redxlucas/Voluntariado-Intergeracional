package com.db.desafio_voluntariado.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_voluntariado.entities.AtividadeDeInteresse;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
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
    
    @PostMapping("/vincular")
    public ResponseEntity<UsuarioDTO> vincularAtividade(
            @RequestParam String email, 
            @RequestParam List<AtividadeDeInteresse> atividadeDeInteresseList) {
        UsuarioDTO usuario = atividadeDeInteresseService.vincularAtividadesAoUsuario(email, atividadeDeInteresseList);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/desvincular/{usuarioId}")
    public ResponseEntity<UsuarioDTO> desvincularAtividade(
            @PathVariable Integer usuarioId, 
            @RequestParam List<AtividadeDeInteresse> atividadeDeInteresseList) {
        UsuarioDTO usuario = atividadeDeInteresseService.desvincularAtividadeDoUsuario(usuarioId, atividadeDeInteresseList);
        return ResponseEntity.ok(usuario);
    }

    // Buscar atividades de interesse de um usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AtividadeDeInteresse>> buscarAtividadesDoUsuario(
            @PathVariable Integer usuarioId) {
        List<AtividadeDeInteresse> atividades = atividadeDeInteresseService.getAtividadePorUsuário(usuarioId);
        return ResponseEntity.ok(atividades);
    }
}

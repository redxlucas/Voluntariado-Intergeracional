package com.db.desafio_voluntariado.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_voluntariado.entities.UsuarioAtividade;
import com.db.desafio_voluntariado.entities.UsuarioAtividadeDTO;
import com.db.desafio_voluntariado.services.UsuarioAtividadeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuario-atividade")
public class UsuarioAtividadeController {
    @Autowired
    private UsuarioAtividadeService usuarioAtividadeService;

    @PostMapping
    public ResponseEntity<UsuarioAtividade> vincularAtividade(
            UsuarioAtividadeDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(usuarioAtividadeService.vincularAtividade(
                dto.getVoluntarioId(), 
                dto.getIdosoId(), 
                dto.getAtividadeId()
            ));
    }
}

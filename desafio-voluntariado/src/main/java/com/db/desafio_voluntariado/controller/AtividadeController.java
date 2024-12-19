package com.db.desafio_voluntariado.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_voluntariado.entities.Atividade;
import com.db.desafio_voluntariado.entities.AtividadeDTO;
import com.db.desafio_voluntariado.services.AtividadeService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/atividade")
public class AtividadeController {
    @Autowired
    private AtividadeService atividadeService;

    @PostMapping
    public ResponseEntity<Atividade> criarAtividade(Atividade atividade) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(atividadeService.adicionarAtividade(atividade));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtividadeDTO> getAtividadeById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(atividadeService.getOne(id));
    }

    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<List<AtividadeDTO>> getAtividadesPorUsuarioId(@PathVariable Integer usuarioId) {
        List<AtividadeDTO> atividades = atividadeService.getByUsuarioId(usuarioId);
        return ResponseEntity.ok(atividades);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<AtividadeDTO>> listarAtividades() {
        return ResponseEntity.ok(atividadeService.getAll());
    }

    @PostMapping("/{atividadeId}/participantes")
    public ResponseEntity<AtividadeDTO> adicionarParticipante(
            @PathVariable Integer atividadeId,
            Integer usuarioUmId, 
            Integer usuarioDoisId) {
        AtividadeDTO atividadeAtualizada = atividadeService.adicionarParticipante(atividadeId, usuarioUmId, usuarioDoisId);
        return ResponseEntity.ok(atividadeAtualizada);
    }
    
    @PutMapping("/concluir/{id}")
        public ResponseEntity<Boolean> concluirAtividades(@PathVariable Integer id) {
            atividadeService.marcarComoConcluida(id);
            return ResponseEntity.ok(true);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtividadeDTO> editarAtividade(@PathVariable Integer id, Atividade atividade) {
        AtividadeDTO atividadeAtualizada = atividadeService.editarAtividade(id, atividade);
        return ResponseEntity.ok(atividadeAtualizada);
    }
}

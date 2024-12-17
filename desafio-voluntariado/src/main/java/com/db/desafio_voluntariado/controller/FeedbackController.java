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

import com.db.desafio_voluntariado.entities.Feedback;
import com.db.desafio_voluntariado.entities.FeedbackDTO;
import com.db.desafio_voluntariado.services.FeedbackService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<FeedbackDTO> criarAtividadeInteresse(Feedback atividadeInteresse) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(feedbackService.adicionarFeedback(atividadeInteresse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getFeedbackById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(feedbackService.getOne(id));
    }

    @GetMapping("/todos")
    public List<Feedback> getAllFeedbacks() {
        return feedbackService.getAll();
    }
}

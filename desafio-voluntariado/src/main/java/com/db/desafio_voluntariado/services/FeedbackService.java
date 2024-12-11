package com.db.desafio_voluntariado.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.desafio_voluntariado.entities.Feedback;
import com.db.desafio_voluntariado.entities.FeedbackDTO;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.FeedbackRepository;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

     public Feedback adicionar(Feedback feedback) {
        if (feedback.getNota() < 1 || feedback.getNota() > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5.");
        }
        if (feedback.getComentario() == null || feedback.getComentario().isEmpty()) {
            throw new IllegalArgumentException("O comentário não pode ser vazio.");
        }
        
        return feedbackRepository.save(feedback);
    }

    public List<FeedbackDTO> getAll(){
        List<Feedback> feedbacks = (List<Feedback>) feedbackRepository.findAll();

        if(feedbacks.isEmpty()){
            throw new NotFoundException("Não há avaliações.");
        }

        return feedbacks.stream()
                .map(feedback -> new FeedbackDTO(feedback.getId(), feedback.getComentario(), feedback.getNota()))
                .collect(Collectors.toList());

    }

    public FeedbackDTO getOneFeedback(Integer id){
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);

        if (feedbackOptional.isPresent()) {
            Feedback feedback = feedbackOptional.get();
            return new FeedbackDTO(feedback.getId(), feedback.getComentario(), feedback.getNota());
        } else {
            throw new NotFoundException("Não há avaliações.");
        }
    }

}

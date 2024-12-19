package com.db.desafio_voluntariado.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.desafio_voluntariado.entities.AtividadeDTO;
import com.db.desafio_voluntariado.entities.Feedback;
import com.db.desafio_voluntariado.entities.FeedbackDTO;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.FeedbackRepository;

import jakarta.transaction.Transactional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private AtividadeService atividadeService;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
     public FeedbackDTO adicionarFeedback(Feedback feedback) {
        if (feedback.getNota() < 1 || feedback.getNota() > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5.");
        }

        Feedback feedbackSalvo = feedbackRepository.save(feedback);

        UsuarioDTO usuarioDTO = usuarioService.getOne(feedback.getUsuario().getId());
        AtividadeDTO atividadeDTO = atividadeService.getOne(feedback.getAtividade().getId());

        return new FeedbackDTO(feedbackSalvo.getId(), feedbackSalvo.getComentario(), 
                feedbackSalvo.getNota(), usuarioDTO, atividadeDTO);
    }

    public List<Feedback> getAll(){
        List<Feedback> feedbacks = (List<Feedback>) feedbackRepository.findAll();
        if(feedbacks.isEmpty()){
            throw new NotFoundException("Não há avaliações.");
        }
        return feedbacks;

    }

    public FeedbackDTO getOne(Integer id) {
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);
        if (feedbackOptional.isEmpty()) {
            throw new NotFoundException("Não há avaliações.");
        }

        Feedback feedback = feedbackOptional.get();
        
        UsuarioDTO usuarioDTO = usuarioService.getOne(feedback.getUsuario().getId());
        AtividadeDTO atividadeDTO = atividadeService.getOne(feedback.getAtividade().getId());

        return new FeedbackDTO(feedback.getId(), feedback.getComentario(), 
                feedback.getNota(), usuarioDTO, atividadeDTO);
    }

    @Transactional
    public List<Feedback> getFeedbacksByAtividadeId(Integer atividadeId) {
        return feedbackRepository.findByAtividadeId(atividadeId);
    }

}

package com.db.desafio_voluntariado.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.desafio_voluntariado.entities.Feedback;
import com.db.desafio_voluntariado.entities.FeedbackDTO;
import com.db.desafio_voluntariado.entities.Usuario;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.FeedbackRepository;

import jakarta.transaction.Transactional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Transactional
     public FeedbackDTO adicionarFeedback(Feedback feedback) {
        if (feedback.getNota() < 1 || feedback.getNota() > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5.");
        }
        if (feedback.getComentario() == null || feedback.getComentario().isEmpty()) {
            throw new IllegalArgumentException("O comentário não pode ser vazio.");
        }

        // Salvar Feedback
        Feedback feedbackSalvo = feedbackRepository.save(feedback);

        // Criar UsuarioDTO a partir do feedback
        Usuario usuario = feedbackSalvo.getUsuario();  // Obter o usuário do feedback
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                usuario.getId(),
                usuario.getNomeCompleto(),
                usuario.getIdade(),
                usuario.getTelefone(),
                usuario.getEmail(),
                "TESTE",
                usuario.getAtividadeDeInteresseList()
        );

        // Criar FeedbackDTO
        FeedbackDTO feedbackDTO = new FeedbackDTO(
                feedbackSalvo.getId(),
                feedbackSalvo.getComentario(),
                feedbackSalvo.getNota(),
                usuarioDTO,
                feedback.getAtividade()
        );

        return feedbackDTO; // Retornar o FeedbackDTO
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
        Usuario usuario = feedback.getUsuario();  // Obter o usuário do feedback
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                usuario.getId(),
                usuario.getNomeCompleto(),
                usuario.getIdade(),
                usuario.getTelefone(),
                usuario.getEmail(),
                "TESTE",
                usuario.getAtividadeDeInteresseList()
        );

        return new FeedbackDTO(
                feedback.getId(),
                feedback.getComentario(),
                feedback.getNota(),
                usuarioDTO,
                feedback.getAtividade()
        );
    }

}

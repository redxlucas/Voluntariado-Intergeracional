package com.db.desafio_voluntariado;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


import com.db.desafio_voluntariado.entities.Feedback;


import com.db.desafio_voluntariado.repository.FeedbackRepository;
import com.db.desafio_voluntariado.services.FeedbackService;


@SpringBootTest(classes = Feedback.class)
public class FeedbackTeste {

     @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    private Feedback feedback;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        feedback = new Feedback();

    }
    

    @Test
    void testLancarExcecaoNotaMenorQueUm() {
     
        feedback.setNota(0);
        feedback.setComentario("Comentário válido");

        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> feedbackService.adicionarFeedback(feedback)
        );
        assertEquals("A nota deve estar entre 1 e 5.", exception.getMessage());
        verify(feedbackRepository, never()).save(any());
    }

    @Test
    void testLancarExcecaoNotaMaiorQueCinco() {

        feedback.setNota(6);
        feedback.setComentario("Comentário válido");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> feedbackService.adicionarFeedback(feedback)
        );
        assertEquals("A nota deve estar entre 1 e 5.", exception.getMessage());
        verify(feedbackRepository, never()).save(any());
    }
}

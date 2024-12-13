package com.db.desafio_voluntariado;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveAdicionarFeedbackQuandoDadosValidos() {
        // Arrange
        Feedback feedback = new Feedback();
        feedback.setNota(4);
        feedback.setComentario("Ótimo serviço!");
        
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

        // Act
        Feedback resultado = feedbackService.adicionar(feedback);

        // Assert
        assertNotNull(resultado);
        assertEquals(4, resultado.getNota());
        assertEquals("Ótimo serviço!", resultado.getComentario());
        verify(feedbackRepository, times(1)).save(feedback);
    }

    @Test
    void deveLancarExcecaoQuandoNotaMenorQueUm() {
        // Arrange
        Feedback feedback = new Feedback();
        feedback.setNota(0);
        feedback.setComentario("Comentário válido");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> feedbackService.adicionar(feedback)
        );
        assertEquals("A nota deve estar entre 1 e 5.", exception.getMessage());
        verify(feedbackRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoNotaMaiorQueCinco() {
        // Arrange
        Feedback feedback = new Feedback();
        feedback.setNota(6);
        feedback.setComentario("Comentário válido");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> feedbackService.adicionar(feedback)
        );
        assertEquals("A nota deve estar entre 1 e 5.", exception.getMessage());
        verify(feedbackRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoComentarioNulo() {
        // Arrange
        Feedback feedback = new Feedback();
        feedback.setNota(3);
        feedback.setComentario(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> feedbackService.adicionar(feedback)
        );
        assertEquals("O comentário não pode ser vazio.", exception.getMessage());
        verify(feedbackRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoComentarioVazio() {
        // Arrange
        Feedback feedback = new Feedback();
        feedback.setNota(3);
        feedback.setComentario("");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> feedbackService.adicionar(feedback)
        );
        assertEquals("O comentário não pode ser vazio.", exception.getMessage());
        verify(feedbackRepository, never()).save(any());
    }
}

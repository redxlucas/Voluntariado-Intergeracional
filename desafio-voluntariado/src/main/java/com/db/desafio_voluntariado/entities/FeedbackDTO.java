package com.db.desafio_voluntariado.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FeedbackDTO {

    @Id
    @GeneratedValue
    private Integer id;
    
    @Lob
    private String comentario;

    @Column(nullable = false)
    private Integer nota;

    private UsuarioDTO usuario;
    
    private AtividadeDTO atividade;
}

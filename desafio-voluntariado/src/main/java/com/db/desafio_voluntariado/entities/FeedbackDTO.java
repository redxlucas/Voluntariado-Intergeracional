package com.db.desafio_voluntariado.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FeedbackDTO {

    @Column(nullable = false)
    private Integer id;
    
    @Lob
    private String comentario;

    @Column(nullable = false)
    private Integer nota;
}

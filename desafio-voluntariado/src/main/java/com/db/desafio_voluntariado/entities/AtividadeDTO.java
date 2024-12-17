package com.db.desafio_voluntariado.entities;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AtividadeDTO {
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String status;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(nullable = false)
    private LocalDateTime dataAtividade;

    @Column(nullable = false)
    private String local;

    private UsuarioDTO idoso;

    private UsuarioDTO voluntario;

    private AtividadeDeInteresse atividadeDeInteresse;
}

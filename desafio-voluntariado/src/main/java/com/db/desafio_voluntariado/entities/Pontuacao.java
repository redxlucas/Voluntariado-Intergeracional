package com.db.desafio_voluntariado.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pontuacao")
public class Pontuacao {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "voluntario_id")
    private Voluntario voluntario;

    private Integer pontuacaoTotal;
    private LocalDateTime ultimaAtualizacao;

    // Getters e setters
}

package com.db.desafio_voluntariado.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "atividade_participante")
public class AtividadeParticipante {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "atividade_id", nullable = false)
    @JsonBackReference("AtividadeParticipantes")
    private Atividade atividade;

    @ManyToOne
    @JoinColumn(name = "idoso_id", nullable = false)
    private Idoso idoso;

    @ManyToOne
    @JoinColumn(name = "voluntario_id", nullable = false)
    private Voluntario voluntario;
}

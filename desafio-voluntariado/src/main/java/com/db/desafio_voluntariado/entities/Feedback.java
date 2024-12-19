package com.db.desafio_voluntariado.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Feedback {
  @Id
  @GeneratedValue
  private Integer id;

  private String comentario;
  @Column(nullable = false)
  private Integer nota;
  @ManyToOne
  @JoinColumn(name = "atividade_id")
  private Atividade atividade;

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;
}

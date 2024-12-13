package com.db.desafio_voluntariado.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Feedback {
@Id
  private Integer id;
  @Lob
  private String comentario;
  @Column(nullable = false)
  private Integer nota;
  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;
}

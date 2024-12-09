package com.db.desafio_voluntariado.entidades;

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
  private String id;
  @Lob
  private String comentario;
  @Column(nullable = false)
  private String nota;
  @ManyToOne
  @JoinColumn(name = "idoso_id")
  private Idosos idosos;
}

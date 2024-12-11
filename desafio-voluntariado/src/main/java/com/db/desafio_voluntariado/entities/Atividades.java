package com.db.desafio_voluntariado.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Atividades {
  @Id
  private String id;
  // presencial ou remoto
  @Column(nullable = false)
  private String tipoDeAtividade;
  @Column(nullable = false)
  private LocalDate dataAtividade;
  private String local;
  @Column(nullable = false)
  private Boolean confirmacao;
  @ManyToOne
  @JoinColumn(name = "area_de_interesse_id")
  private AreasDeInteresse areasDeInteresse;
}
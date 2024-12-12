package com.db.desafio_voluntariado.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Atividade {
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
}
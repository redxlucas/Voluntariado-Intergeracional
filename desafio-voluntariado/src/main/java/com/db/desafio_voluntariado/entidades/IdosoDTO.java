package com.db.desafio_voluntariado.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class IdosoDTO {
  @Id
  private String id;
  @Column(nullable = false)
  private String nomeCompleto;
  @Column(nullable = false)
  private String telefone;
}


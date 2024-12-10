package com.db.desafio_voluntariado.entidades;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IdosoDTO {
  @Column(nullable = false)
  private Integer id;
  @Column(nullable = false)
  private String nomeCompleto;
  @Column(nullable = false)
  private String telefone;
  @Column(nullable = false)
  private String email;
}

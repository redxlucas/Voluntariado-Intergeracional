package com.db.desafio_voluntariado.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "atividade")
public class Atividade {
  @Id
  @GeneratedValue
  private Integer id;
  // presencial ou remoto
  @Column(nullable = false)
  private String tipoDeAtividade;
  private String descricao;
  // @Column(nullable = false)
  // private LocalDate dataAtividade;
  // private String local;
  // @Column(nullable = false)
  // private Boolean confirmacao;

  @OneToMany(mappedBy = "atividade")
  private List<UsuarioAtividade> usuarioAtividades;
}
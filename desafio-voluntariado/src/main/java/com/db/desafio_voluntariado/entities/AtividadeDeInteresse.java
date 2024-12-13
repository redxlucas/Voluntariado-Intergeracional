package com.db.desafio_voluntariado.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class AtividadeDeInteresse {
  @Id
  @GeneratedValue
  private Integer id;
  @Column(nullable = false)
  private String nome;

  @ManyToMany(mappedBy = "atividadeDeInteresseList")
  @JsonBackReference
  private List<Usuario> usuarios = new ArrayList<>();
}

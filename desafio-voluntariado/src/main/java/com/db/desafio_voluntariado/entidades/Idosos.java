package com.db.desafio_voluntariado.entidades;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;


@Data
@Entity
public class Idosos {
  @Id
  private String id;
  @Column(nullable = false)
  private String nomeCompleto;
  private String endereco;
  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String senha;
  @Column(nullable = false)
  private String cpf;
  @Column(nullable = false)
  private String telefone;
  @Column(nullable = false)
  private String nomeResponsavel;
  @Column(nullable = false)
  private String telefoneResponsavel;
  @OneToOne
  @JoinColumn(name = "idoso_dto")
  private IdosoDTO idosoDto;
  @OneToMany(mappedBy = "idosos")
  private List<AreasDeInteresse> areasDeInteresseList = new ArrayList<>();
  @OneToMany(mappedBy = "idosos")
  private List<Feedback> feedbackList = new ArrayList<>();

}

package com.db.desafio_voluntariado.entities;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Data;


@Data
@Entity
public class Idoso {
  @Id
  @GeneratedValue
  private Integer id;
  @Column(nullable = false)
  private String nomeCompleto;
  @Column(nullable = false)
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataDeNascimento;
  private Integer idade;
  @Column(nullable = false)
  private String cep;
  @Column(nullable = false)
  private String bairro;
  @Column(nullable = false)
  private String cidade;
  @Column(nullable = false)
  private String estado;
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

  @OneToMany(mappedBy = "idosos")
  private List<AreasDeInteresse> areasDeInteresseList = new ArrayList<>();
  @OneToMany(mappedBy = "idosos")
  private List<Feedback> feedbackList = new ArrayList<>();

  @PrePersist
  public void calcularIdade() {
    this.idade = Period.between(this.dataDeNascimento, LocalDate.now()).getYears();
  }
}

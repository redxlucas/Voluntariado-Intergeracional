package com.db.desafio_voluntariado.entities;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Usuario {
  @Id
  @GeneratedValue
  private Integer id;

  @Column(nullable = false)
  private String nomeCompleto;

  @JsonFormat(pattern = "dd-MM-yyyy")
  @DateTimeFormat(pattern = "dd-MM-yyyy")
  private LocalDate dataDeNascimento;

  private Integer idade;

  @Column(nullable = false)
  private String cpf;

  @Column(nullable = false)
  private String cep;

  @Column(nullable = false)
  private String rua;

  private String complemento;

  @Column(nullable = false)
  private String bairro;

  @Column(nullable = false)
  private String cidade;

  @Column(nullable = false)
  private String estado;

  @Column(nullable = false)
  private String telefone;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String senha;

  @Column(nullable = false)
  private String tipoDeUsuario; // voluntario ou idoso

  @Column(nullable = false)
  private Integer totalPontuacao; // voluntario

  private String nomeResponsavel; // idoso

  private String telefoneResponsavel; // idoso
  
  @ManyToMany
  @JoinTable(
        name = "usuario_atividade_de_interesse",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "atividade_de_interesse_id")
  )
  @JsonManagedReference
  private List<AtividadeDeInteresse> atividadeDeInteresseList;

  @PrePersist
  public void calcularIdade() {
    if(dataDeNascimento != null) {
      this.idade = Period.between(this.dataDeNascimento, LocalDate.now()).getYears();
    }
  }
}

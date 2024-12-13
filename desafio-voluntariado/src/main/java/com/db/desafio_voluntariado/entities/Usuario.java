package com.db.desafio_voluntariado.entities;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
public abstract class Usuario {
  @Id
  @GeneratedValue
  private Integer id;

  @Column(nullable = false)
  private String nomeCompleto;

  @JsonFormat(pattern = "dd-MM-yyyy")
  @DateTimeFormat(pattern = "dd-MM-yyyy")
  @Column(nullable = false)
  private LocalDate dataDeNascimento;

  private Integer idade;

  @Column(nullable = false, unique = true)
  private String cpf;

  @Column(nullable = false)
  private String cep;

  @Column(nullable = false)
  private String bairro;

  @Column(nullable = false)
  private String cidade;

  @Column(nullable = false)
  private String estado;

  @Column(nullable = false)
  private String telefone;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String senha;

  @ManyToMany
  @JoinTable(
        name = "usuario_atividade_de_interesse",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "atividade_de_interesse_id")
    )
    @JsonManagedReference
    private List<AtividadeDeInteresse> atividadeDeInteresseList = new ArrayList<>();

  @PrePersist
  public void calcularIdade() {
    if(dataDeNascimento != null) {
      this.idade = Period.between(this.dataDeNascimento, LocalDate.now()).getYears();
    }
  }
}

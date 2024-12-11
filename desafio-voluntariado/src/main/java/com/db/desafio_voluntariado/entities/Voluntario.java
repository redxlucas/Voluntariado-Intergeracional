package com.db.desafio_voluntariado.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Voluntario {
    
  @Id
  @GeneratedValue
  private Integer id;
  @Column(nullable = false)
  private String nomeCompleto;
  @Column(nullable = false)
  private String endereco;
  @Column(nullable = false)
  private String idade;
  @Column(nullable = false)
  private String cpf;
  @Column(nullable = false)
  private String telefone;
  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String senha;

  @OneToMany(mappedBy = "voluntarios")
  private List<AreasDeInteresse> areasDeInteresseList = new ArrayList<>();
}

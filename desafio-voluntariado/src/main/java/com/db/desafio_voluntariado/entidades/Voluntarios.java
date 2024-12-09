package com.db.desafio_voluntariado.entidades;

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
public class Voluntarios {
    
    @Id
  @GeneratedValue
  private String id;
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
  @OneToMany(mappedBy = "voluntarios")
  private List<AreasDeInteresse> areasDeInteresseList = new ArrayList<>();
}

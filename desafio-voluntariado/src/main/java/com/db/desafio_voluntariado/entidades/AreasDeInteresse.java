package com.db.desafio_voluntariado.entidades;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class AreasDeInteresse {

   @Id
  @GeneratedValue
  private String id;
  @Column(nullable = false)
  private String areaDeInteresse;
  @Lob
  private String descricao;
  @ManyToOne
  @JoinColumn(name = "voluntario_id")
  private Voluntarios voluntarios;
  @ManyToOne
  @JoinColumn(name = "idoso_id")
  private Idoso idosos;
  @OneToMany(mappedBy = "areasDeInteresse")
  private List<Atividades> atividadesList = new ArrayList<>();

}

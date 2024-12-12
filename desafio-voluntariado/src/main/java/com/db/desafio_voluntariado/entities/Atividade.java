package com.db.desafio_voluntariado.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "atividade", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference("AtividadeParticipantes")
    private List<AtividadeParticipante> participantes = new ArrayList<>();
    
    public void adicionarParticipante(Idoso idoso, Voluntario voluntario) {
      AtividadeParticipante participante = new AtividadeParticipante();
      participante.setAtividade(this);
      participante.setIdoso(idoso);
      participante.setVoluntario(voluntario);
      this.participantes.add(participante);
  }
}
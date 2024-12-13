package com.db.desafio_voluntariado.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String status;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(nullable = false)
    private LocalDateTime dataAtividade;

    @Column(nullable = false)
    private String local;

    @OneToMany(mappedBy = "atividade", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference("AtividadeParticipantes")
    private List<AtividadeParticipante> participantes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "atividadeDeInteresse_id")
    private AtividadeDeInteresse atividadeDeInteresse;
    
    public void adicionarParticipante(Idoso idoso, Voluntario voluntario) {
      AtividadeParticipante participante = new AtividadeParticipante();
      participante.setAtividade(this);
      participante.setIdoso(idoso);
      participante.setVoluntario(voluntario);
      this.participantes.add(participante);
  }
}
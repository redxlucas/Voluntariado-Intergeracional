package com.db.desafio_voluntariado.entities;

import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario_atividade")
public class UsuarioAtividade {
    @EmbeddedId
    private UsuarioAtividadeId id;

    @ManyToOne
    @MapsId("voluntarioId")
    @JoinColumn(name = "voluntario_id")
    private Voluntario voluntario;

    @ManyToOne
    @MapsId("idosoId")
    @JoinColumn(name = "idoso_id")
    private Idoso idoso;

    @ManyToOne
    @MapsId("atividadeId")
    @JoinColumn(name = "atividade_id")
    private Atividade atividade;

    private LocalDateTime dataVinculacao;
}

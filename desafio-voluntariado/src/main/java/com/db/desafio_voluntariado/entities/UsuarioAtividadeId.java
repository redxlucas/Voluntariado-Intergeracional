package com.db.desafio_voluntariado.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class UsuarioAtividadeId implements Serializable {
    @Column(name = "voluntario_id")
    private Integer voluntarioId;

    @Column(name = "idoso_id")
    private Integer idosoId;

    @Column(name = "atividade_id")
    private Integer atividadeId;
}

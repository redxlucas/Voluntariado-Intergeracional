package com.db.desafio_voluntariado.entities;

import lombok.Data;

@Data
public class UsuarioAtividadeDTO {
    private Integer voluntarioId;
    private Integer idosoId;
    private Integer atividadeId;
}
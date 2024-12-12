package com.db.desafio_voluntariado.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("VOLUNTARIO")
public class Voluntario extends Usuario {
    private String especialidade;
    private Integer totalPontos;
}

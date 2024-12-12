package com.db.desafio_voluntariado.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("VOLUNTARIO")
public class Voluntario extends Usuario {
    private Integer totalPontos;
}

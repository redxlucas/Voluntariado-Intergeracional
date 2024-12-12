package com.db.desafio_voluntariado.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("IDOSO")
public class Idoso extends Usuario {
    private String nomeResponsavel;
    private String telefoneResponsavel;
}

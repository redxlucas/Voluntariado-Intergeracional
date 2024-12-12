package com.db.desafio_voluntariado.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("IDOSO")
public class Idoso extends Usuario {
    private String nomeResponsavel;
    private String telefoneResponsavel;
}

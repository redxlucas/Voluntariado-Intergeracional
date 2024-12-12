package com.db.desafio_voluntariado.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UsuarioDTO {

    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(nullable = false)
    private Integer idade;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonProperty("tipo_usuario")
    private String tipoUsuario;
    
    private List<AtividadeDeInteresse> atividadeDeInteresse;
}

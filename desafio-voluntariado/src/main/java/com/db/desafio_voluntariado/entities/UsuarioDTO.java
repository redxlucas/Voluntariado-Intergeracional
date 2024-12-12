package com.db.desafio_voluntariado.entities;


import java.util.List;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
    private String tipoDeUsuario;
    
    private List<AtividadeDeInteresse> atividadeDeInteresse;
}

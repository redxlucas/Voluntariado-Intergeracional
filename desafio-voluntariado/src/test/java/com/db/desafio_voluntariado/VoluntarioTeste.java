package com.db.desafio_voluntariado;


import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.db.desafio_voluntariado.entities.Voluntario;
import com.db.desafio_voluntariado.repository.VoluntarioRepository;

public class VoluntarioTeste {

    private Voluntario voluntario;

    @Autowired
    private VoluntarioRepository voluntarioRepository;

     @BeforeEach
    public void setup(){
        voluntario = new Voluntario();

    }

    @Test
    public void setTest(){
        voluntario.setNomeCompleto("Maria Julia Fane");
        voluntario.setDataDeNascimento(LocalDate.of(2024, 12, 11));
        voluntario.setCep("12345-678");
        voluntario.setBairro("Partenon");
        voluntario.setCidade("Porto Alegre");
        voluntario.setEstado("RS");
        voluntario.setCpf("123.456.789-00");
        voluntario.setTelefone("(11) 98765-4321");
        voluntario.setEmail("maju.fane@example.com");
        voluntario.setSenha("senha123");
        voluntario.setDisponibilidade("Tarde");
    }

    
}

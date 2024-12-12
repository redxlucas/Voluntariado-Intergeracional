package com.db.desafio_voluntariado;


import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.db.desafio_voluntariado.entities.Usuario;
import com.db.desafio_voluntariado.repository.UsuarioRepository;

public class UsuarioTeste {

    private Usuario usuario;

    @Autowired
    private UsuarioRepository usuarioRepository;

     @BeforeEach
    public void setup(){
        usuario = new Usuario();

    }

    @Test
    public void setTest(){
        usuario.setNomeCompleto("Maria Julia Fane");
        usuario.setDataDeNascimento(LocalDate.of(2024, 12, 11));
        usuario.setCep("12345-678");
        usuario.setBairro("Partenon");
        usuario.setCidade("Porto Alegre");
        usuario.setEstado("RS");
        usuario.setCpf("123.456.789-00");
        usuario.setTelefone("(11) 98765-4321");
        usuario.setEmail("maju.fane@example.com");
        usuario.setSenha("senha123");
        // usuario.setDisponibilidade("Tarde");
    }

    
}

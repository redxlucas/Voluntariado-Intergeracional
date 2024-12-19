package com.db.desafio_voluntariado;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.db.desafio_voluntariado.controller.VoluntarioController;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.entities.Voluntario;

@SpringBootTest
public class VoluntarioControllerSemIsolamento {

    @Autowired
    private VoluntarioController voluntarioController;

    @Test
    void deveAdicionarEBuscarVoluntarioComSucesso() {
        Voluntario voluntario = new Voluntario();
        voluntario.setNomeCompleto("Maria Santos");
        voluntario.setCpf("987.654.321-00");
        voluntario.setEmail("maria@email.com");
        voluntario.setDataDeNascimento(LocalDate.of(1995, 5, 15));
        voluntario.setCep("87654-321");
        voluntario.setBairro("Jardim");
        voluntario.setCidade("Rio de Janeiro");
        voluntario.setEstado("RJ");
        voluntario.setTelefone("21988888888");
        voluntario.setSenha("senha456");

        ResponseEntity<UsuarioDTO> responsePost = voluntarioController.adicionarVoluntario(voluntario);

        assertNotNull(responsePost.getBody());
        assertNotNull(responsePost.getBody().getId());
        assertEquals("Maria Santos", responsePost.getBody().getNomeCompleto());
        assertEquals("maria@email.com", responsePost.getBody().getEmail());

        ResponseEntity<UsuarioDTO> responseGet = voluntarioController.getVoluntarioById(responsePost.getBody().getId());
        
        assertNotNull(responseGet.getBody());
        assertEquals("Maria Santos", responseGet.getBody().getNomeCompleto());
        assertEquals("maria@email.com", responseGet.getBody().getEmail());
    }
}
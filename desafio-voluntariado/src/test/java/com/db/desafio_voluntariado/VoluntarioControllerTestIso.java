package com.db.desafio_voluntariado;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import com.db.desafio_voluntariado.entities.Voluntario;
import com.db.desafio_voluntariado.repository.VoluntarioRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class VoluntarioControllerTestIso {

    @Autowired
    private MockMvc mockMvc; 
    
    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Test
    public void testAdicionarVoluntario() throws Exception {

        String voluntarioJson = """
            {
                "nomeCompleto": "João Silva",
                "idade": 30,
                "email": "joao.silva@email.com"
            }
        """;

        mockMvc.perform(post("/voluntario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(voluntarioJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeCompleto").value("João Silva"));

        List<Voluntario> voluntarios = (List<Voluntario>) voluntarioRepository.findAll();
        assertThat(voluntarios).hasSize(1);
        assertThat(voluntarios.get(0).getNomeCompleto()).isEqualTo("João Silva");
    }
}

package com.db.desafio_voluntariado;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.db.desafio_voluntariado.controller.VoluntarioController;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.entities.Voluntario;
import com.db.desafio_voluntariado.services.VoluntarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(VoluntarioController.class)
public class VoluntarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoluntarioService voluntarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Voluntario voluntario;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        voluntario = new Voluntario();
        voluntario.setId(1);
        voluntario.setNomeCompleto("João Silva");
        voluntario.setCpf("123.456.789-00");
        voluntario.setEmail("joao@email.com");
        voluntario.setDataDeNascimento(LocalDate.of(1990, 1, 1));
        voluntario.setCep("12345-678");
        voluntario.setBairro("Centro");
        voluntario.setCidade("São Paulo");
        voluntario.setEstado("SP");
        voluntario.setTelefone("11999999999");
        voluntario.setSenha("senha123");

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1);
        usuarioDTO.setNomeCompleto("João Silva");
        usuarioDTO.setEmail("joao@email.com");
    }

    @Test
    void deveAdicionarVoluntarioComSucesso() throws Exception {
        when(voluntarioService.adicionarVoluntario(any(Voluntario.class))).thenReturn(usuarioDTO);

        mockMvc.perform(post("/voluntario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voluntario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nomeCompleto").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    void deveBuscarVoluntarioPorIdComSucesso() throws Exception {
        when(voluntarioService.getOne(1)).thenReturn(usuarioDTO);

        mockMvc.perform(get("/voluntario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nomeCompleto").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    void deveListarTodosVoluntariosComSucesso() throws Exception {
        List<UsuarioDTO> usuarios = Arrays.asList(usuarioDTO);
        when(voluntarioService.getAll()).thenReturn(usuarios);

        mockMvc.perform(get("/voluntario/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nomeCompleto").value("João Silva"))
                .andExpect(jsonPath("$[0].email").value("joao@email.com"));
    }
}
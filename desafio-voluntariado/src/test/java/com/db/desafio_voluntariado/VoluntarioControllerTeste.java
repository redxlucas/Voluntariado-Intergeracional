package com.db.desafio_voluntariado;

import com.db.desafio_voluntariado.controller.VoluntarioController;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.entities.Voluntario;
import com.db.desafio_voluntariado.services.VoluntarioService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;


@WebMvcTest(VoluntarioController.class)
public class VoluntarioControllerTeste {

  @Autowired
    private MockMvc mockMvc; 

    @MockBean
    private VoluntarioService voluntarioService; 
  

    private Voluntario voluntario;
    private UsuarioDTO usuarioDTO;


    @BeforeEach
    public void setUp() {
        voluntario = new Voluntario(); 
        voluntario.setId(1);
        voluntario.setNomeCompleto("John Doe");
        voluntario.setEmail("johndoe@example.com");
        usuarioDTO = new UsuarioDTO();
       
        usuarioDTO.setId(1);
        usuarioDTO.setNomeCompleto("John Doe");
        usuarioDTO.setEmail("johndoe@example.com");
    }

    @Test
    public void testAdicionarVoluntario() throws Exception {
        
        when(voluntarioService.adicionarVoluntario(any(Voluntario.class))).thenReturn(usuarioDTO);

        
        mockMvc.perform(MockMvcRequestBuilders.post("/voluntario")
                .param("nomeCompleto", "John Doe") 
                .param("email", "johndoe@example.com")
                .param("senha", "password123"))
                .andDo(print()) 
                .andExpect(MockMvcResultMatchers.status().isCreated()) 
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeCompleto").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("johndoe@example.com"));
    }

    @Test 
    void testGetOneVoluntario() throws Exception{

        UsuarioDTO usuarioDTO = new UsuarioDTO();
     
        usuarioDTO.setId(2);
        usuarioDTO.setNomeCompleto("Erika Fane");
        when(voluntarioService.getOne(2)).thenReturn(usuarioDTO);

    
        mockMvc.perform(get("/voluntario/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nomeCompleto").value("Erika Fane"));

    }

    @Test 
    void testGetAllVoluntarios() throws Exception{
        UsuarioDTO usuarioDTO = new UsuarioDTO();
     
        usuarioDTO.setId(2);
        usuarioDTO.setNomeCompleto("Erika Fane");

        UsuarioDTO usuarioDTO2 = new UsuarioDTO();
        usuarioDTO2.setId(3);
        usuarioDTO2.setNomeCompleto("Emory Scott");

        List<UsuarioDTO> usuarios = Arrays.asList(usuarioDTO, usuarioDTO2);

        when(voluntarioService.getAll()).thenReturn(usuarios);

        mockMvc.perform(get("/voluntario/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].nomeCompleto").value("Erika Fane"))
                .andExpect(jsonPath("$[1].id").value(3))
                .andExpect(jsonPath("$[1].nomeCompleto").value("Emory Scott"));
    }


}

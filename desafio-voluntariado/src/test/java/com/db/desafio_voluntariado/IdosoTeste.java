package com.db.desafio_voluntariado;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.db.desafio_voluntariado.entities.Idoso;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.IdosoRepository;
import com.db.desafio_voluntariado.services.IdosoService;


@SpringBootTest(classes = Idoso.class)
public class IdosoTeste {

        @InjectMocks
    private IdosoService idosoService;

    @Mock
    private IdosoRepository idosoRepository;

    private Idoso idoso;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        idoso = new Idoso();
    }

    @Test
    void testGetOne() {
        idoso.setId(1);
        idoso.setNomeCompleto("Antônio Conceição");

        when(idosoRepository.findById(1)).thenReturn(Optional.of(idoso));

        UsuarioDTO usuarioDTO = idosoService.getOne(1);

        assertNotNull(usuarioDTO);
        assertEquals(1, usuarioDTO.getId());
        assertEquals("Antônio Conceição", usuarioDTO.getNomeCompleto());
        verify(idosoRepository, times(1)).findById(1);
    }

    @Test
    void testGetOneNotFoundException() {
        when(idosoRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> idosoService.getOne(1));

        assertEquals("Idoso não encontrado.", exception.getMessage());
        verify(idosoRepository, times(1)).findById(1);
    }

    @Test
    void testGetAll() {
        Idoso idoso1 = new Idoso();
        idoso1.setId(1);
        idoso1.setNomeCompleto("Antônio Conceição");

        Idoso idoso2 = new Idoso();
        idoso2.setId(2);
        idoso2.setNomeCompleto("Cleusa Miniano");

        List<Idoso> idosos = Arrays.asList(idoso1, idoso2);

        when(idosoRepository.findAll()).thenReturn(idosos);

        List<UsuarioDTO> usuarioDTOList = idosoService.getAll();

        assertNotNull(usuarioDTOList);
        assertEquals(2, usuarioDTOList.size());
        assertEquals("Antônio Conceição", usuarioDTOList.get(0).getNomeCompleto());
        assertEquals("Cleusa Miniano", usuarioDTOList.get(1).getNomeCompleto());
        verify(idosoRepository, times(1)).findAll();
    }

    @Test
    void testGetAllNotFoundException() {
        when(idosoRepository.findAll()).thenReturn(Arrays.asList());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> idosoService.getAll());

        assertEquals("Idoso(s) não encontrado(s)", exception.getMessage());
        verify(idosoRepository, times(1)).findAll();
    }
}

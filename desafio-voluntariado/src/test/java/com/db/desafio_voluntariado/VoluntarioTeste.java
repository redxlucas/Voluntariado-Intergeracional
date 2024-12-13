package com.db.desafio_voluntariado;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.db.desafio_voluntariado.entities.Usuario;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.entities.Voluntario;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.VoluntarioRepository;
import com.db.desafio_voluntariado.services.VoluntarioService;

@SpringBootTest(classes = Voluntario.class)
public class VoluntarioTeste {

    @InjectMocks
    private VoluntarioService voluntarioService;

    @Mock
    private VoluntarioRepository voluntarioRepository;

    private Voluntario voluntario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        voluntario = new Voluntario();
    }

    @Test
    void testAdicionarUsuario() {
        voluntario = new Voluntario();
        voluntario.setId(1);
        voluntario.setNomeCompleto("João Silva");

        when(voluntarioRepository.save(voluntario)).thenReturn(voluntario);

        Usuario savedUsuario = voluntarioService.adicionarVoluntario(voluntario);

        assertNotNull(savedUsuario);
        assertEquals(1, savedUsuario.getId());
        assertEquals("João Silva", savedUsuario.getNomeCompleto());
        verify(voluntarioRepository, times(1)).save(voluntario);
    }

    @Test
    void testGetOne() {
        voluntario = new Voluntario();
        voluntario.setId(1);
        voluntario.setNomeCompleto("João Silva");

        when(voluntarioRepository.findById(1)).thenReturn(Optional.of(voluntario));

        UsuarioDTO usuarioDTO = voluntarioService.getOne(1);

        assertNotNull(usuarioDTO);
        assertEquals(1, usuarioDTO.getId());
        assertEquals("João Silva", usuarioDTO.getNomeCompleto());
        verify(voluntarioRepository, times(1)).findById(1);
    }

    @Test
    void testGetOneNotFoundException() {
        when(voluntarioRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> voluntarioService.getOne(1));

        assertEquals("Voluntario não encontrado.", exception.getMessage());
        verify(voluntarioRepository, times(1)).findById(1);
    }

    @Test
    void testGetAll() {
        Voluntario voluntario1 = new Voluntario();
        voluntario1.setId(1);
        voluntario1.setNomeCompleto("João Silva");

        Voluntario voluntario2 = new Voluntario();
        voluntario2.setId(2);
        voluntario2.setNomeCompleto("Maria Oliveira");

        List<Voluntario> voluntarios = Arrays.asList(voluntario1, voluntario2);

        when(voluntarioRepository.findAll()).thenReturn(voluntarios);

        List<UsuarioDTO> usuarioDTOList = voluntarioService.getAll();

        assertNotNull(usuarioDTOList);
        assertEquals(2, usuarioDTOList.size());
        assertEquals("João Silva", usuarioDTOList.get(0).getNomeCompleto());
        assertEquals("Maria Oliveira", usuarioDTOList.get(1).getNomeCompleto());
        verify(voluntarioRepository, times(1)).findAll();
    }

    @Test
    void testGetAllNotFoundException() {
        when(voluntarioRepository.findAll()).thenReturn(Arrays.asList());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> voluntarioService.getAll());

        assertEquals("Voluntario(s) não encontrado(s)", exception.getMessage());
        verify(voluntarioRepository, times(1)).findAll();

    
    }
    @Test
    public void setTest(){
        voluntario.setNomeCompleto("Maria Julia Antunes");
        voluntario.setDataDeNascimento(LocalDate.of(2024, 12, 11));
        voluntario.setCep("12345-678");
        voluntario.setBairro("Partenon");
        voluntario.setCidade("Porto Alegre");
        voluntario.setEstado("RS");
        voluntario.setCpf("123.456.789-00");
        voluntario.setTelefone("(11) 98765-4321");
        voluntario.setEmail("maju.antunes@example.com");
        voluntario.setSenha("senha123");
        voluntario.setTotalPontos(4);
    }

//     @Test
//     void testManyToManyRelationship() {
//         usuario.setId(1);
//         usuario.setNomeCompleto("Erika Fane");

//         AtividadeDeInteresse atividade1 = new AtividadeDeInteresse();
//         atividade1.setId(1);
//         atividade1.setTipoDeAtividade("Passeio no parque");

//         AtividadeDeInteresse atividade2 = new AtividadeDeInteresse();
//         atividade2.setId(2);
//         atividade2.setTipoDeAtividade("Tarde de jogos");

//         List<AtividadeDeInteresse> atividades = new ArrayList<>();
//         atividades.add(atividade1);
//         atividades.add(atividade2);

//         usuario.setAtividadeDeInteresseList(atividades);

//         assertNotNull(usuario.getAtividadeDeInteresseList());
//         assertEquals(2, usuario.getAtividadeDeInteresseList().size());
//         assertEquals("Passeio no parque", usuario.getAtividadeDeInteresseList().get(0).getTipoDeAtividade());
//         assertEquals("Tarde de jogos", usuario.getAtividadeDeInteresseList().get(1).getTipoDeAtividade());
// }

    @Test
    void testCalcularIdade() {
        
        voluntario.setDataDeNascimento(LocalDate.of(2000, Month.JANUARY, 1));

        voluntario.calcularIdade();

        int expectedAge = LocalDate.now().getYear() - 2000;
        if (LocalDate.now().getDayOfYear() < LocalDate.of(2000, Month.JANUARY, 1).getDayOfYear()) {
            expectedAge--;
        }

        assertEquals(expectedAge, voluntario.getIdade());
    }

    @Test
    void testCalcularIdadeSemDataDeNascimento() {
        
        voluntario.setDataDeNascimento(null);

        voluntario.calcularIdade();

        assertNull(voluntario.getIdade());
    }
}
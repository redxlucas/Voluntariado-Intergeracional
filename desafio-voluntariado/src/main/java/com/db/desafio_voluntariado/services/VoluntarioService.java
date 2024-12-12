package com.db.desafio_voluntariado.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.desafio_voluntariado.entities.Voluntario;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.VoluntarioRepository;

import jakarta.transaction.Transactional;

@Service
public class VoluntarioService {
    @Autowired
    private VoluntarioRepository voluntarioRepository;

    // @Autowired
    // private PontuacaoRepository pontosRepository;

    @Transactional
    public Voluntario adicionarVoluntario(Voluntario voluntario){
        return voluntarioRepository.save(voluntario);
    }

        public List<UsuarioDTO> getAll() {
        List<Voluntario> voluntarioList = (List<Voluntario>) voluntarioRepository.findAll();
        if (voluntarioList.isEmpty()) {
            throw new NotFoundException("Voluntario(s) não encontrado(s)");
        }
        return voluntarioList.stream().map(
                voluntario -> new UsuarioDTO(voluntario.getId(), voluntario.getNomeCompleto(), voluntario.getIdade(),
                        voluntario.getTelefone(), voluntario.getEmail(), "VOLUNTARIO", voluntario.getAtividadeDeInteresseList()))
                .collect(Collectors.toList());
    }

    public UsuarioDTO getOne(Integer id) {
        Optional<Voluntario> voluntarioOptional = voluntarioRepository.findById(id);

        if (voluntarioOptional.isPresent()) {
            Voluntario voluntario = voluntarioOptional.get();
            return new UsuarioDTO(voluntario.getId(), voluntario.getNomeCompleto(), voluntario.getIdade(), voluntario.getTelefone(),
                    voluntario.getEmail(), "VOLUNTARIO", voluntario.getAtividadeDeInteresseList());
        } else {
            throw new NotFoundException("Voluntario não encontrado.");
        }
    }

    // @Transactional
    // public Pontuacao adicionarPontos(Integer voluntarioId, int pontosGanhos) {
    //     Voluntario voluntario = voluntarioRepository.findById(voluntarioId)
    //         .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado"));
        
    //     voluntario.adicionarPontos(pontosGanhos);
    //     voluntarioRepository.save(voluntario);
        
    //     return voluntario.getPontos();
    // }
}

package com.db.desafio_voluntariado.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.entities.Voluntario;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.VoluntarioRepository;

@Service
public class VoluntariosService {

    @Autowired
    public VoluntarioRepository voluntarioRepository;

    public Voluntario addVoluntario(Voluntario voluntario) {
        return voluntarioRepository.save(voluntario);
    }

    public UsuarioDTO getOne(Integer id) {
        Optional<Voluntario> voluntarioOptional = voluntarioRepository.findById(id);

        if (voluntarioOptional.isPresent()) {
            Voluntario voluntario = voluntarioOptional.get();
            return new UsuarioDTO(voluntario.getId(), voluntario.getNomeCompleto(), voluntario.getIdade(),  voluntario.getTelefone(),
                    voluntario.getEmail());
        } else {
            throw new NotFoundException("Voluntário não encontrado.");
        }
    }

    public List<UsuarioDTO> getAll() {
        List<Voluntario> voluntarioList = (List<Voluntario>) voluntarioRepository.findAll();
        
        if (voluntarioList.isEmpty()) {
            throw new NotFoundException("Voluntários não encontrados");
        }

        return voluntarioList.stream()
                .map(voluntario -> new UsuarioDTO(voluntario.getId(), voluntario.getNomeCompleto(), voluntario.getIdade(), voluntario.getTelefone(), voluntario.getEmail()))
                .collect(Collectors.toList());
    }
}

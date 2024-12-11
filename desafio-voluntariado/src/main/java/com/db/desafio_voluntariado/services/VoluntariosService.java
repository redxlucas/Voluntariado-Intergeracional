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
    public VoluntarioRepository voluntariosRepository;

    public Voluntario addVoluntarios(Voluntario voluntarios) {
        return voluntariosRepository.save(voluntarios);
    }

    public UsuarioDTO getOne(Integer id) {
        Optional<Voluntario> voluOptional = voluntariosRepository.findById(id);

        if (voluOptional.isPresent()) {
            Voluntario voluntarios = voluOptional.get();
            return new UsuarioDTO(voluntarios.getId(), voluntarios.getNomeCompleto(), voluntarios.getTelefone(),
                    voluntarios.getEmail());
        } else {
            throw new NotFoundException("Voluntário não encontrado.");
        }
    }

    public List<UsuarioDTO> getAll() {
        List<Voluntario> voluntariosList = (List<Voluntario>) voluntariosRepository.findAll();
        
        if (voluntariosList.isEmpty()) {
            throw new NotFoundException("Voluntários não encontrados");
        }

        return voluntariosList.stream()
                .map(voluntario -> new UsuarioDTO(voluntario.getId(), voluntario.getNomeCompleto(),voluntario.getTelefone(), voluntario.getEmail()))
                .collect(Collectors.toList());
    }
}

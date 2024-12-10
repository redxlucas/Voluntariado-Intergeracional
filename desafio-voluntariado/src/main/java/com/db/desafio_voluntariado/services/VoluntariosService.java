package com.db.desafio_voluntariado.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.desafio_voluntariado.entidades.Voluntarios;
import com.db.desafio_voluntariado.entidades.VoluntariosDTO;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.VoluntariosRepository;

@Service
public class VoluntariosService {

    @Autowired
    public VoluntariosRepository voluntariosRepository;

    public Voluntarios addVoluntarios(Voluntarios voluntarios) {
        return voluntariosRepository.save(voluntarios);
    }

    public VoluntariosDTO getOne(Integer id) {
        Optional<Voluntarios> voluOptional = voluntariosRepository.findById(id);

        if (voluOptional.isPresent()) {
            Voluntarios voluntarios = voluOptional.get();
            return new VoluntariosDTO(voluntarios.getId(), voluntarios.getNomeCompleto(), voluntarios.getTelefone(),
                    voluntarios.getEmail());
        } else {
            throw new NotFoundException("Voluntário não encontrado.");
        }
    }

    public List<VoluntariosDTO> getAll() {
        List<Voluntarios> voluntariosList = (List<Voluntarios>) voluntariosRepository.findAll();
        
        if (voluntariosList.isEmpty()) {
            throw new NotFoundException("Voluntários não encontrados");
        }

        return voluntariosList.stream()
                .map(voluntario -> new VoluntariosDTO(voluntario.getId(), voluntario.getNomeCompleto(),voluntario.getTelefone(), voluntario.getEmail()))
                .collect(Collectors.toList());
    }
}

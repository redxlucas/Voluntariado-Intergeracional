package com.db.desafio_voluntariado.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.desafio_voluntariado.entidades.Idoso;
import com.db.desafio_voluntariado.entidades.IdosoDTO;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.IdosoRepository;

@Service
public class IdosoService {

    @Autowired
    public IdosoRepository idosoRepository;

    public Idoso adicionar(Idoso idoso) {
        return idosoRepository.save(idoso);
    }

    public IdosoDTO getOne(Integer id) {
        Optional<Idoso> idosoOptional = idosoRepository.findById(id);

        if (idosoOptional.isPresent()) {
            Idoso idoso = idosoOptional.get();
            return new IdosoDTO(idoso.getId(), idoso.getNomeCompleto(), idoso.getTelefone(), idoso.getEmail());
        } else {
            throw new NotFoundException("Idoso não encontrado.");
        }
    }

    public List<IdosoDTO> getAll() {
        List<Idoso> idosoList = (List<Idoso>) idosoRepository.findAll();
        if(idosoList.isEmpty()) {
            throw new NotFoundException("Idoso(s) não encontrado(s)");
        }
        return idosoList.stream().map(idoso -> new IdosoDTO(idoso.getId(), idoso.getNomeCompleto(), idoso.getTelefone(), idoso.getEmail())).collect(Collectors.toList());
    }
}

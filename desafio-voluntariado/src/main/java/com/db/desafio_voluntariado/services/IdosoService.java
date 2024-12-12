package com.db.desafio_voluntariado.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.desafio_voluntariado.entities.Idoso;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.IdosoRepository;

import jakarta.transaction.Transactional;

@Service
public class IdosoService {
    @Autowired
    private IdosoRepository idosoRepository;

    @Transactional
    public Idoso adicionarIdoso(Idoso idoso) {
        return idosoRepository.save(idoso);
    }

    public List<UsuarioDTO> getAll() {
        List<Idoso> idosoList = (List<Idoso>) idosoRepository.findAll();
        if (idosoList.isEmpty()) {
            throw new NotFoundException("Idoso(s) não encontrado(s)");
        }
        return idosoList.stream().map(
                idoso -> new UsuarioDTO(idoso.getId(), idoso.getNomeCompleto(), idoso.getIdade(),
                        idoso.getTelefone(), idoso.getEmail(), "IDOSO", idoso.getAtividadeDeInteresseList()))
                .collect(Collectors.toList());
    }

    public UsuarioDTO getOne(Integer id) {
        Optional<Idoso> idosoOptional = idosoRepository.findById(id);

        if (idosoOptional.isPresent()) {
            Idoso idoso = idosoOptional.get();
            return new UsuarioDTO(idoso.getId(), idoso.getNomeCompleto(), idoso.getIdade(), idoso.getTelefone(),
                    idoso.getEmail(), "IDOSO", idoso.getAtividadeDeInteresseList());
        } else {
            throw new NotFoundException("Idoso não encontrado.");
        }
    }
}

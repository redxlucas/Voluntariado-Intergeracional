package com.db.desafio_voluntariado.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.desafio_voluntariado.entities.Idoso;
import com.db.desafio_voluntariado.entities.Usuario;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDTO getOne(Integer id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return new UsuarioDTO(usuario.getId(), usuario.getNomeCompleto(), usuario.getIdade(), 
                    usuario.getTelefone(), usuario.getEmail(), usuario instanceof Idoso ? "IDOSO" : "VOLUNTARIO", 
                    usuario.getAtividadeDeInteresseList());
        } else {
            throw new NotFoundException("Usuario não encontrado.");
        }
    }

    public List<UsuarioDTO> getAll() {
        List<Usuario> usuarioList = (List<Usuario>) usuarioRepository.findAll();
        if (usuarioList.isEmpty()) {
            throw new NotFoundException("Usuario(s) não encontrado(s)");
        }

        return usuarioList.stream()
                .map(usuario -> {
                    return new UsuarioDTO(usuario.getId(), usuario.getNomeCompleto(), usuario.getIdade(), 
                    usuario.getTelefone(), usuario.getEmail(), usuario instanceof Idoso ? "IDOSO" : "VOLUNTARIO", 
                    usuario.getAtividadeDeInteresseList());
                })
                .collect(Collectors.toList());
    }
}

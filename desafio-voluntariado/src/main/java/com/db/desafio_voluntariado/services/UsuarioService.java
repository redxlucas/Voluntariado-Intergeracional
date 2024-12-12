package com.db.desafio_voluntariado.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.desafio_voluntariado.entities.Idoso;
import com.db.desafio_voluntariado.entities.Usuario;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.entities.Voluntario;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario add(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public UsuarioDTO getOne(Integer id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            String tipoUsuario = null;
            if (usuario instanceof Idoso) {
                tipoUsuario = "IDOSO";
            } else if (usuario instanceof Voluntario) {
                tipoUsuario = "VOLUNTARIO";
            }

            if (tipoUsuario == null) {
                throw new IllegalArgumentException("Tipo de usuário não encontrado.");
            }

            return new UsuarioDTO(usuario.getId(), usuario.getNomeCompleto(), usuario.getIdade(), 
                    usuario.getTelefone(), usuario.getEmail(), tipoUsuario, 
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
                    String tipoUsuario = null;
                    if (usuario instanceof Idoso) {
                        tipoUsuario = "IDOSO";
                    } else if (usuario instanceof Voluntario) {
                        tipoUsuario = "VOLUNTARIO";
                    }

                    if (tipoUsuario == null) {
                        throw new IllegalArgumentException("Tipo de usuário não encontrado.");
                    }

                    return new UsuarioDTO(usuario.getId(), usuario.getNomeCompleto(), usuario.getIdade(),
                            usuario.getTelefone(), usuario.getEmail(), tipoUsuario,
                            usuario.getAtividadeDeInteresseList());
                })
                .collect(Collectors.toList());
    }
}

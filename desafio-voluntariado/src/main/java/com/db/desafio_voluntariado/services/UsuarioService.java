package com.db.desafio_voluntariado.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.desafio_voluntariado.entities.AtividadeDeInteresse;
import com.db.desafio_voluntariado.entities.Idoso;
import com.db.desafio_voluntariado.entities.Usuario;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.UsuarioRepository;

import jakarta.persistence.criteria.Join;

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
                            usuario.getTelefone(), usuario.getEmail(),
                            usuario instanceof Idoso ? "IDOSO" : "VOLUNTARIO",
                            usuario.getAtividadeDeInteresseList());
                })
                .collect(Collectors.toList());
    }

    public Page<UsuarioDTO> filtrarUsuarios(String email, List<Integer> atividadeDeInteresseId, Pageable pageable) {
        Specification<Usuario> spec = Specification.where(null);

        if (StringUtils.hasText(email)) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("email")),
                    "%" + email.toLowerCase() + "%"));
        }

        if (atividadeDeInteresseId != null && !atividadeDeInteresseId.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<Usuario, AtividadeDeInteresse> joinAtividade = root.join("atividadeDeInteresseList");
                return criteriaBuilder.in(joinAtividade.get("id")).value(atividadeDeInteresseId);
            });
        }

        Page<Usuario> usuariosPage = usuarioRepository.findAll(spec, pageable);

        Page<UsuarioDTO> usuarioDTOPage = usuariosPage.map(usuario -> {
            UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getId(), usuario.getNomeCompleto(), usuario.getIdade(),
                    usuario.getTelefone(), usuario.getEmail(), usuario instanceof Idoso ? "IDOSO" : "VOLUNTARIO",
                    usuario.getAtividadeDeInteresseList());

            return usuarioDTO;
        });

        return usuarioDTOPage;
    }

    public Boolean login(String email, String senha) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isEmpty()) {
            throw new NotFoundException("Email não encontrado.");
        }

        if (!senha.equals(usuarioOptional.get().getSenha())) {
            return false;
        }
        return true;
    }

    public UsuarioDTO getUsuarioByEmail(String email) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return new UsuarioDTO(usuario.getId(), usuario.getNomeCompleto(), usuario.getIdade(),
                    usuario.getTelefone(), usuario.getEmail(), usuario instanceof Idoso ? "IDOSO" : "VOLUNTARIO",
                    usuario.getAtividadeDeInteresseList());
        } else {
            throw new NotFoundException("Usuario não encontrado.");
        }
    }
}

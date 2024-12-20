package com.db.desafio_voluntariado.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.desafio_voluntariado.entities.AtividadeDeInteresse;
import com.db.desafio_voluntariado.entities.Usuario;
import com.db.desafio_voluntariado.entities.UsuarioDTO;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.AtividadeDeInteresseRepository;
import com.db.desafio_voluntariado.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AtividadeDeInteresseService {

    @Autowired
    private AtividadeDeInteresseRepository atividadeDeInteresseRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public AtividadeDeInteresse add(AtividadeDeInteresse atividadeDeInteresse) {
        return atividadeDeInteresseRepository.save(atividadeDeInteresse);
    }

    public List<AtividadeDeInteresse> getAll() {
        List<AtividadeDeInteresse> atividadeDeInteresseList = (List<AtividadeDeInteresse>) atividadeDeInteresseRepository
                .findAll();
        if (atividadeDeInteresseList.isEmpty()) {
            throw new NotFoundException("Atividade(s) de Interesse não encontrada(s)");
        }
        return atividadeDeInteresseList;
    }

    @Transactional
    public UsuarioDTO vincularAtividadesAoUsuario(String email, List<AtividadeDeInteresse> atividadesDeInteresse) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (atividadesDeInteresse != null && !atividadesDeInteresse.isEmpty()) {
            for (AtividadeDeInteresse atividade : atividadesDeInteresse) {
                if (!usuario.getAtividadeDeInteresseList().contains(atividade)) {
                    usuario.getAtividadeDeInteresseList().add(atividade);
                }
            }
        }

        usuarioRepository.save(usuario);

        return usuarioService.getOne(usuario.getId());
    }

    @Transactional
    public UsuarioDTO desvincularAtividadeDoUsuario(Integer usuarioId, List<AtividadeDeInteresse> atividadesDeInteresse) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (atividadesDeInteresse != null && !atividadesDeInteresse.isEmpty()) {
            for (AtividadeDeInteresse atividade : atividadesDeInteresse) {
                if (usuario.getAtividadeDeInteresseList().contains(atividade)) {
                    usuario.getAtividadeDeInteresseList().remove(atividade);
                }
            }
        }

        usuarioRepository.save(usuario);

        return usuarioService.getOne(usuario.getId());
    }

    public List<AtividadeDeInteresse> getAtividadePorUsuário(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return usuario.getAtividadeDeInteresseList();
    }

    public AtividadeDeInteresse getOne(Integer id) {
        return atividadeDeInteresseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Atividade de Interesse não encontrada com ID: " + id));
    }
}

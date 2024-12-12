package com.db.desafio_voluntariado.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.desafio_voluntariado.entities.AtividadeDeInteresse;
import com.db.desafio_voluntariado.entities.Usuario;
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

    @Transactional
    public AtividadeDeInteresse add(AtividadeDeInteresse atividadeDeInteresse) {
        return atividadeDeInteresseRepository.save(atividadeDeInteresse);
    }

    public List<AtividadeDeInteresse> getAll() {
        List<AtividadeDeInteresse> atividadeDeInteresseList = (List<AtividadeDeInteresse>) atividadeDeInteresseRepository.findAll();
        if(atividadeDeInteresseList.isEmpty()){
            throw new NotFoundException("Atividade(s) de Interesse não encontrada(s)");
        }
        return atividadeDeInteresseList;
    }

    // Buscar atividade por ID
    public Optional<AtividadeDeInteresse> buscarAtividadePorId(Integer id) {
        return atividadeDeInteresseRepository.findById(id);
    }

    // Vincular uma atividade de interesse a um usuário
    @Transactional
    public Usuario vincularAtividadeAoUsuario(Integer usuarioId, Integer atividadeId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        
        AtividadeDeInteresse atividade = atividadeDeInteresseRepository.findById(atividadeId)
                .orElseThrow(() -> new EntityNotFoundException("Atividade de interesse não encontrada"));
        
        usuario.getAtividadeDeInteresseList().add(atividade);
        return usuarioRepository.save(usuario);
    }

    // Desvincular uma atividade de interesse de um usuário
    @Transactional
    public Usuario desvincularAtividadeDoUsuario(Integer usuarioId, Integer atividadeId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        
        AtividadeDeInteresse atividade = atividadeDeInteresseRepository.findById(atividadeId)
                .orElseThrow(() -> new EntityNotFoundException("Atividade de interesse não encontrada"));
        
        usuario.getAtividadeDeInteresseList().remove(atividade);
        return usuarioRepository.save(usuario);
    }

    // Buscar todas as atividades de interesse de um usuário
    public List<AtividadeDeInteresse> buscarAtividadesDoUsuario(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        
        return usuario.getAtividadeDeInteresseList();
    }
}

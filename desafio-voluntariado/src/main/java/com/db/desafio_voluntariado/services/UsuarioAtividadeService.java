package com.db.desafio_voluntariado.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.desafio_voluntariado.entities.Atividade;
import com.db.desafio_voluntariado.entities.Idoso;
import com.db.desafio_voluntariado.entities.UsuarioAtividade;
import com.db.desafio_voluntariado.entities.UsuarioAtividadeId;
import com.db.desafio_voluntariado.entities.Voluntario;
import com.db.desafio_voluntariado.repository.AtividadeRepository;
import com.db.desafio_voluntariado.repository.IdosoRepository;
import com.db.desafio_voluntariado.repository.UsuarioAtividadeRepository;
import com.db.desafio_voluntariado.repository.VoluntarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UsuarioAtividadeService {
    @Autowired
    private UsuarioAtividadeRepository usuarioAtividadeRepository;
    @Autowired
    private VoluntarioRepository voluntarioRepository;
    @Autowired
    private IdosoRepository idosoRepository;
    @Autowired
    private AtividadeRepository atividadeRepository;

    @Transactional
    public UsuarioAtividade vincularAtividade(Integer voluntarioId, Integer idosoId, Integer atividadeId) {
        Voluntario voluntario = voluntarioRepository.findById(voluntarioId)
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado"));

        Idoso idoso = idosoRepository.findById(idosoId)
                .orElseThrow(() -> new EntityNotFoundException("Idoso não encontrado"));

        Atividade atividade = atividadeRepository.findById(atividadeId)
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada"));

        UsuarioAtividadeId id = new UsuarioAtividadeId();
        id.setVoluntarioId(voluntarioId);
        id.setIdosoId(idosoId);
        id.setAtividadeId(atividadeId);

        UsuarioAtividade usuarioAtividade = new UsuarioAtividade();
        usuarioAtividade.setId(id);
        usuarioAtividade.setVoluntario(voluntario);
        usuarioAtividade.setIdoso(idoso);
        usuarioAtividade.setAtividade(atividade);
        usuarioAtividade.setDataVinculacao(LocalDateTime.now());

        return usuarioAtividadeRepository.save(usuarioAtividade);
    }
}

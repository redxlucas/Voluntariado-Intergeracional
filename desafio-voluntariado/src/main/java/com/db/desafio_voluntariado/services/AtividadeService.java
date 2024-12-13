package com.db.desafio_voluntariado.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.desafio_voluntariado.entities.Atividade;
import com.db.desafio_voluntariado.entities.AtividadeDeInteresse;
import com.db.desafio_voluntariado.entities.Idoso;
import com.db.desafio_voluntariado.entities.Voluntario;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.AtividadeDeInteresseRepository;
import com.db.desafio_voluntariado.repository.AtividadeRepository;
import com.db.desafio_voluntariado.repository.IdosoRepository;
import com.db.desafio_voluntariado.repository.VoluntarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private AtividadeDeInteresseRepository atividadeDeInteresseRepository;

    @Autowired
    private IdosoRepository idosoRepository;

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Transactional
    public Atividade adicionarAtividade(Atividade atividade) {
        AtividadeDeInteresse atividadeDeInteresse = atividadeDeInteresseRepository.findById(atividade.getAtividadeDeInteresse().getId())
                .orElseThrow(() -> new NotFoundException("Atividade de interesse não encontrado"));
        
        atividade.setNome(atividadeDeInteresse.getNome());
        return atividadeRepository.save(atividade);
    }

    public Atividade getOne(Integer id) {
        Optional<Atividade> atividadeOptional = atividadeRepository.findById(id);

        if (atividadeOptional.isEmpty()) {
            throw new NotFoundException("Atividade não encontrado.");
        }
        return atividadeOptional.get();
    }

    public List<Atividade> getAll() {
        List<Atividade> atividadeList = (List<Atividade>) atividadeRepository.findAll();
        if (atividadeList.isEmpty()) {
            throw new NotFoundException("Atividade(s) não encontrado(s)");
        }
        return atividadeList;
    }


    @Transactional
    public Atividade adicionarParticipante(Integer atividadeId, Integer idosoId, Integer voluntarioId) {
        Atividade atividade = getOne(atividadeId);
        
        Idoso idoso = idosoRepository.findById(idosoId)
                .orElseThrow(() -> new EntityNotFoundException("Idoso não encontrado com ID: " + idosoId));
        
        Voluntario voluntario = voluntarioRepository.findById(voluntarioId)
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado com ID: " + voluntarioId));
        
        atividade.adicionarParticipante(idoso, voluntario);
        
        return atividadeRepository.save(atividade);
    }
}

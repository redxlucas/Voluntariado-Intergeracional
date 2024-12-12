package com.db.desafio_voluntariado.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.desafio_voluntariado.entities.AtividadeDeInteresse;
import com.db.desafio_voluntariado.exception.NotFoundException;
import com.db.desafio_voluntariado.repository.AtividadeDeInteresseRepository;

@Service
public class AtividadeDeInteresseService {
    
    @Autowired
    private AtividadeDeInteresseRepository atividadeDeInteresseRepository;

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
}

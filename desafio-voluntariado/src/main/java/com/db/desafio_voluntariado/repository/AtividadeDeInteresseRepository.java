package com.db.desafio_voluntariado.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.db.desafio_voluntariado.entities.AtividadeDeInteresse;

@Repository
public interface AtividadeDeInteresseRepository extends CrudRepository<AtividadeDeInteresse, Integer> {
    
}

package com.db.desafio_voluntariado.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.db.desafio_voluntariado.entidades.Idoso;

@Repository
public interface IdosoRepository extends CrudRepository<Idoso, Integer> {
    
}

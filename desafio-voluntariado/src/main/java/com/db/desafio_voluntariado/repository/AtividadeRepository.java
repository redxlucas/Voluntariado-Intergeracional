package com.db.desafio_voluntariado.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.db.desafio_voluntariado.entities.Atividade;

@Repository
public interface AtividadeRepository extends CrudRepository<Atividade, Integer>{
}

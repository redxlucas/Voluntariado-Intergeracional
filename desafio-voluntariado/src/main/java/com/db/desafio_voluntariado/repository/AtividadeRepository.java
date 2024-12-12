package com.db.desafio_voluntariado.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.db.desafio_voluntariado.entities.Atividade;

@Repository
public interface AtividadeRepository extends CrudRepository<Atividade, Integer>{
    List<Atividade> findByDescricaoContaining(String descricao);
}

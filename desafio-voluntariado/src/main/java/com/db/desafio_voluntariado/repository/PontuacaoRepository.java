package com.db.desafio_voluntariado.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.db.desafio_voluntariado.entities.Pontuacao;

@Repository
public interface PontuacaoRepository extends CrudRepository<Pontuacao, Integer> {
    Optional<Pontuacao> findByVoluntarioId(Integer voluntarioId);
}

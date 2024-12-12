package com.db.desafio_voluntariado.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.db.desafio_voluntariado.entities.Idoso;

@Repository
public interface IdosoRepository extends CrudRepository<Idoso, Integer> {
    List<Idoso> findByNecessidadesEspeciais(String necessidadesEspeciais);
}

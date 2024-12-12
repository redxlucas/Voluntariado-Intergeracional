package com.db.desafio_voluntariado.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.db.desafio_voluntariado.entities.Voluntario;

@Repository
public interface VoluntarioRepository extends CrudRepository<Voluntario, Integer> {
    List<Voluntario> findByEspecialidade(String especialidade);
}

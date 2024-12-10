package com.db.desafio_voluntariado.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.db.desafio_voluntariado.entidades.Voluntarios;

@Repository
public interface VoluntariosRepository extends CrudRepository<Voluntarios, Integer> {

}

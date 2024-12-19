package com.db.desafio_voluntariado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.db.desafio_voluntariado.entities.Atividade;

@Repository
public interface AtividadeRepository extends CrudRepository<Atividade, Integer>{
        @Query("SELECT a FROM Atividade a " +
           "JOIN a.participantes p " +
           "WHERE p.idoso.id = :usuarioId OR p.voluntario.id = :usuarioId")
    List<Atividade> findByUsuarioId(@Param("usuarioId") Integer usuarioId);
}

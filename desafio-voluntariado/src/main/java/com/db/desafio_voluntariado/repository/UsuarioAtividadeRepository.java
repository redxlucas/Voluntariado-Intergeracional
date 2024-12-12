package com.db.desafio_voluntariado.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.db.desafio_voluntariado.entities.UsuarioAtividade;
import com.db.desafio_voluntariado.entities.UsuarioAtividadeId;

@Repository
public interface UsuarioAtividadeRepository extends CrudRepository<UsuarioAtividade, UsuarioAtividadeId> {
    List<UsuarioAtividade> findByVoluntarioId(Integer voluntarioId);
    List<UsuarioAtividade> findByIdosoId(Integer idosoId);
    List<UsuarioAtividade> findByAtividadeId(Integer atividadeId);
}

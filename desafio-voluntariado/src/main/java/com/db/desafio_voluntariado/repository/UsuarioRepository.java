package com.db.desafio_voluntariado.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.db.desafio_voluntariado.entities.Usuario;
import com.db.desafio_voluntariado.entities.UsuarioDTO;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>, JpaSpecificationExecutor<Usuario>{
    Optional<Usuario> findByNomeCompleto(String nomeCompleto);
    Optional<Usuario> findByEmail(String email);
}

package com.db.desafio_voluntariado.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.db.desafio_voluntariado.entities.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    Optional<Usuario> findByNomeCompleto(String nomeCompleto);
    Optional<Usuario> findByEmail(String email);
}

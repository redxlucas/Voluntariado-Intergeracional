package com.db.desafio_voluntariado.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.db.desafio_voluntariado.entities.Feedback;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {
    List<Feedback> findByAtividadeId(Integer atividadeId);
} 

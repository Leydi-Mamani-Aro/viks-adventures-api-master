package com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
}

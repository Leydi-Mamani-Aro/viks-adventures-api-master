package com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
    @Query("SELECT o FROM Option o WHERE o.question.id = :questionId")
    List<Option> findOptionsByQuestionId(@Param("questionId") Long questionId);

    @Query("SELECT o FROM Option o WHERE o.question.quiz.id = :quizId")
    List<Option> findOptionsByQuizId(Long quizId);
}
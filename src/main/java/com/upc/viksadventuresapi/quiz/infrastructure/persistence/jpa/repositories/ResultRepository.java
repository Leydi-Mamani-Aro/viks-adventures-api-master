package com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    @Query("SELECT o FROM Result o WHERE o.profile.id = :profileId")
    Optional<Result> findResultsByProfileId(@Param("profileId") Long profileId);

    @Query("SELECT o FROM Result o WHERE o.profile.id = :profileId AND o.quiz.id = :quizId")
    Optional<Result> findResultByProfileIdAndQuizId(@Param("profileId") Long profileId, @Param("quizId")Long quizId);
}

package com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    @Query("SELECT r FROM Response r WHERE r.profile.id = :profileId")
    Optional<Response> findResponsesByProfileId(@Param("profileId") Long profileId);

    @Query("SELECT r FROM Response r WHERE r.profile.id = :profileId AND r.option.question.quiz.id = :quizId")
    List<Response> findResponsesByProfileIdAndQuizId(@Param("profileId") Long profileId, @Param("quizId") Long quizId);

    @Query("SELECT COUNT(r) FROM Response r WHERE r.profile.id = :profileId " +
            "AND r.option.question.quiz.id = :quizId")
    int countResponsesByProfileIdAndQuizId(@Param("profileId") Long profileId,
                                           @Param("quizId") Long quizId);
}

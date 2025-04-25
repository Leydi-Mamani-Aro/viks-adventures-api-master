package com.upc.viksadventuresapi.quiz.domain.services;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Quiz;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetAllQuizzesQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetQuizByIdQuery;

import java.util.List;
import java.util.Optional;

public interface QuizQueryService {
    Optional<Quiz> handle(GetQuizByIdQuery query);
    List<Quiz> handle(GetAllQuizzesQuery query);
}

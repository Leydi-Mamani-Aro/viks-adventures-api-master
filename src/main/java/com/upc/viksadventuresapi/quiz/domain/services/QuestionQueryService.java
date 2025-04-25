package com.upc.viksadventuresapi.quiz.domain.services;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Question;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetAllQuestionsQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetQuestionByIdQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetQuestionsByQuizIdQuery;

import java.util.List;
import java.util.Optional;

public interface QuestionQueryService {
    Optional<Question> handle(GetQuestionByIdQuery query);
    List<Question> handle(GetAllQuestionsQuery query);
    Optional<Question> handle(GetQuestionsByQuizIdQuery query);
}

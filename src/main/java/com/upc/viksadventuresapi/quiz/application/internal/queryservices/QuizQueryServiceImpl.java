package com.upc.viksadventuresapi.quiz.application.internal.queryservices;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Quiz;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetAllQuizzesQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetQuizByIdQuery;
import com.upc.viksadventuresapi.quiz.domain.services.QuizQueryService;
import com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizQueryServiceImpl implements QuizQueryService {
    private final QuizRepository quizRepository;

    public QuizQueryServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public Optional<Quiz> handle(GetQuizByIdQuery query) {
        return quizRepository.findById(query.quizId());
    }

    @Override
    public List<Quiz> handle(GetAllQuizzesQuery query) {
        return quizRepository.findAll();
    }
}

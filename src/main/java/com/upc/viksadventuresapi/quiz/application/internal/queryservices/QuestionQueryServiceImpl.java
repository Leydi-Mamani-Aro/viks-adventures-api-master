package com.upc.viksadventuresapi.quiz.application.internal.queryservices;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Question;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetAllQuestionsQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetQuestionByIdQuery;
import com.upc.viksadventuresapi.quiz.domain.model.queries.GetQuestionsByQuizIdQuery;
import com.upc.viksadventuresapi.quiz.domain.services.QuestionQueryService;
import com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionQueryServiceImpl implements QuestionQueryService {
    private final QuestionRepository questionRepository;

    public QuestionQueryServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Optional<Question> handle(GetQuestionByIdQuery query) {
        return questionRepository.findById(query.questionId());
    }

    @Override
    public List<Question> handle(GetAllQuestionsQuery query) {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> handle(GetQuestionsByQuizIdQuery query) {
        Long quizId = query.quizId();
        return questionRepository.findQuestionsByQuizId(quizId);
    }

}

package com.upc.viksadventuresapi.quiz.application.internal.queryservices;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Option;
import com.upc.viksadventuresapi.quiz.domain.model.queries.*;
import com.upc.viksadventuresapi.quiz.domain.services.OptionQueryService;
import com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories.OptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OptionQueryServiceImpl implements OptionQueryService {
    public final OptionRepository optionRepository;

    public OptionQueryServiceImpl(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Override
    public Optional<Option> handle(GetOptionByIdQuery query) {
        return optionRepository.findById(query.optionId());
    }

    @Override
    public List<Option> handle(GetAllOptionsQuery query) {
        return optionRepository.findAll();
    }

    @Override
    public List<Option> handle(GetOptionsByQuestionIdQuery query) {
        Long questionId = query.questionId();
        return optionRepository.findOptionsByQuestionId(questionId);
    }

    @Override
    public List<Option> handle(GetOptionsByQuizIdQuery query) {
        Long quizId = query.quizId();
        return optionRepository.findOptionsByQuizId(quizId);
    }

    @Override
    public List<Option> handle(GetOptionsByCorrectAndQuizIdQuery query) {
        Long quizId = query.quizId();
        return optionRepository.findOptionsByQuizId(quizId).stream()
                .filter(Option::isCorrect)
                .collect(Collectors.toList());
    }
}
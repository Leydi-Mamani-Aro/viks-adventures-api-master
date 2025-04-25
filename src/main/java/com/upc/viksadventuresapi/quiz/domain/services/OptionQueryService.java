package com.upc.viksadventuresapi.quiz.domain.services;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Option;
import com.upc.viksadventuresapi.quiz.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface OptionQueryService {
    Optional<Option> handle(GetOptionByIdQuery query);
    List<Option> handle(GetAllOptionsQuery query);
    List<Option> handle(GetOptionsByQuestionIdQuery query);
    List<Option> handle(GetOptionsByQuizIdQuery query);
    List<Option> handle(GetOptionsByCorrectAndQuizIdQuery query);
}

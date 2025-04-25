package com.upc.viksadventuresapi.quiz.domain.services;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Question;
import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateQuestionCommand;
import com.upc.viksadventuresapi.quiz.domain.model.commands.DeleteQuestionByIdCommand;

import java.util.Optional;

public interface QuestionCommandService {
    Optional<Question> handle(CreateQuestionCommand command);
    void handle(DeleteQuestionByIdCommand command);
}

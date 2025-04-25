package com.upc.viksadventuresapi.quiz.domain.services;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Quiz;
import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateQuizCommand;
import com.upc.viksadventuresapi.quiz.domain.model.commands.DeleteQuizByIdCommand;

import java.util.Optional;

public interface QuizCommandService {
    Optional<Quiz> handle(CreateQuizCommand command);
    void handle(DeleteQuizByIdCommand command);
}

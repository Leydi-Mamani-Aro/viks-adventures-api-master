package com.upc.viksadventuresapi.quiz.domain.services;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Result;
import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateOrUpdateResultCommand;
import com.upc.viksadventuresapi.quiz.domain.model.commands.DeleteResultByIdCommand;

import java.util.Optional;

public interface ResultCommandService {
    Optional<Result> handle(CreateOrUpdateResultCommand command);
    void handle(DeleteResultByIdCommand command);
}

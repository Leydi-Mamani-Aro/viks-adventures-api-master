package com.upc.viksadventuresapi.quiz.domain.services;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Response;
import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateResponseCommand;
import com.upc.viksadventuresapi.quiz.domain.model.commands.DeleteResponseByIdCommand;

import java.util.Optional;

public interface ResponseCommandService {
    Optional<Response> handle(CreateResponseCommand command);
    void handle(DeleteResponseByIdCommand command);
}

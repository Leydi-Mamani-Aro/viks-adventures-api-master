package com.upc.viksadventuresapi.quiz.domain.services;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Option;
import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateOptionCommand;
import com.upc.viksadventuresapi.quiz.domain.model.commands.DeleteOptionByIdCommand;

import java.util.Optional;

public interface OptionCommandService {
    Optional<Option> handle(CreateOptionCommand command);
    void handle(DeleteOptionByIdCommand command);
}

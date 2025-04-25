package com.upc.viksadventuresapi.quiz.interfaces.rest.transform;

import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateOptionCommand;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.CreateOptionResource;

public class CreateOptionCommandFromResourceAssembler {
    public static CreateOptionCommand toCommandFromResource(CreateOptionResource resource) {
        return new CreateOptionCommand(resource.questionId(), resource.optionText(), resource.isCorrect());
    }
}

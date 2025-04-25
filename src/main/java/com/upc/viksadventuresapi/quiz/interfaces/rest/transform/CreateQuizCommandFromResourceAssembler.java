package com.upc.viksadventuresapi.quiz.interfaces.rest.transform;

import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateQuizCommand;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.CreateQuizResource;

public class CreateQuizCommandFromResourceAssembler {
    public static CreateQuizCommand toCommandFromResource(CreateQuizResource resource) {
        return new CreateQuizCommand(resource.title(), resource.description());
    }
}

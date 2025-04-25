package com.upc.viksadventuresapi.quiz.interfaces.rest.transform;

import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateQuestionCommand;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.CreateQuestionResource;

public class CreateQuestionCommandFromResourceAssembler {
    public static CreateQuestionCommand toCommandFromResource(CreateQuestionResource resource) {
        return new CreateQuestionCommand(resource.quizId(), resource.performance(), resource.questionText(), resource.imageUrl());
    }
}

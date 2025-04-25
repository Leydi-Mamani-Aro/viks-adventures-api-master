package com.upc.viksadventuresapi.quiz.interfaces.rest.transform;

import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateOrUpdateResultCommand;

public class CreateResultCommandFromResourceAssembler {
    public static CreateOrUpdateResultCommand toCommandFromResource(CreateOrUpdateResultCommand resource) {
        return new CreateOrUpdateResultCommand(resource.quizId(), resource.profileId(), resource.score());
    }
}

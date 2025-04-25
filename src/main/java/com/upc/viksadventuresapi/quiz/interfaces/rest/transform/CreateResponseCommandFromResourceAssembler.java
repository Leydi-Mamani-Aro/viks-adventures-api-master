package com.upc.viksadventuresapi.quiz.interfaces.rest.transform;

import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateResponseCommand;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.CreateResponseResource;

public class CreateResponseCommandFromResourceAssembler {
    public static CreateResponseCommand toCommandFromResource(CreateResponseResource resource) {
        return new CreateResponseCommand( resource.optionId(), resource.profileId());
    }
}

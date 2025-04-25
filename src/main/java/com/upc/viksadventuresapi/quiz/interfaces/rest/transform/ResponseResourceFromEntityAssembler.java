package com.upc.viksadventuresapi.quiz.interfaces.rest.transform;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Response;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.ResponseResource;

public class ResponseResourceFromEntityAssembler {
    public static ResponseResource toResourceFromEntity(Response entity) {
        return new ResponseResource(entity.getId(), entity.getOptionId(), entity.getProfileId());
    }
}

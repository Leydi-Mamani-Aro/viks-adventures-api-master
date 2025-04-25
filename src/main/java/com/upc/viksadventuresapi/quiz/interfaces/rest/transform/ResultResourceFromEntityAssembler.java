package com.upc.viksadventuresapi.quiz.interfaces.rest.transform;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Result;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.ResultResource;

public class ResultResourceFromEntityAssembler {
    public static ResultResource toResourceFromEntity(Result entity) {
        return new ResultResource(entity.getId(), entity.getQuizId(), entity.getProfileId(), entity.getScore());
    }
}

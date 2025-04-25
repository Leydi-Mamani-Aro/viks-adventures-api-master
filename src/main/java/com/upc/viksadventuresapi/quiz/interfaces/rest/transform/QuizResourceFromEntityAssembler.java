package com.upc.viksadventuresapi.quiz.interfaces.rest.transform;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Quiz;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.QuizResource;

public class QuizResourceFromEntityAssembler {
    public static QuizResource toResourceFromEntity(Quiz entity) {
        return new QuizResource(entity.getId(), entity.getTitle(), entity.getDescription());
    }
}

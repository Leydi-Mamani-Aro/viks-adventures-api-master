package com.upc.viksadventuresapi.quiz.interfaces.rest.transform;

import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Option;
import com.upc.viksadventuresapi.quiz.interfaces.rest.resources.OptionResource;

public class OptionResourceFromEntityAssembler {
    public static OptionResource toResourceFromEntity(Option entity) {
        return new OptionResource(entity.getId(), entity.getQuestionId(), entity.getOptionText(), entity.isCorrect());
    }
}

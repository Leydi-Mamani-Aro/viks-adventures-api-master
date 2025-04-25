package com.upc.viksadventuresapi.quiz.interfaces.rest.resources;

public record OptionResource(
        Long id,
        Long questionId,
        String optionText,
        Boolean isCorrect
) {
}

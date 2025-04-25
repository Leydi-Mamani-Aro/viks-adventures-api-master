package com.upc.viksadventuresapi.quiz.interfaces.rest.resources;

public record CreateOptionResource(
        Long questionId,
        String optionText,
        Boolean isCorrect
) {
}

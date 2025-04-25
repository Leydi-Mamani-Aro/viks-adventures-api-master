package com.upc.viksadventuresapi.quiz.domain.model.commands;

public record CreateOptionCommand(
        Long questionId,
        String optionText,
        boolean isCorrect
) {
}

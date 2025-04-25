package com.upc.viksadventuresapi.quiz.domain.model.commands;

public record CreateQuestionCommand(
        Long quizId,
        int performance,
        String questionText,
        String imageUrl
) {
}

package com.upc.viksadventuresapi.quiz.interfaces.rest.resources;

public record CreateQuestionResource(
        Long quizId,
        int performance,
        String questionText,
        String imageUrl
) {
}

package com.upc.viksadventuresapi.quiz.interfaces.rest.resources;

public record QuestionResource(
        Long id,
        Long quizId,
        int performance,
        String questionText,
        String imageUrl
) {
}

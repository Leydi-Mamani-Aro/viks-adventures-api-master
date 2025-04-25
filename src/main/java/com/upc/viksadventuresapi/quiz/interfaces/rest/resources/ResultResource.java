package com.upc.viksadventuresapi.quiz.interfaces.rest.resources;

public record ResultResource(
        Long id,
        Long quizId,
        Long profileId,
        int score
) {
}

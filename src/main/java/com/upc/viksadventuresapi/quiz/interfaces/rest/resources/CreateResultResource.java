package com.upc.viksadventuresapi.quiz.interfaces.rest.resources;

public record CreateResultResource(
        Long quizId,
        Long profileId,
        int score
) {
}

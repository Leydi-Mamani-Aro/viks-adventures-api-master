package com.upc.viksadventuresapi.quiz.domain.model.commands;

public record CreateOrUpdateResultCommand(
        Long quizId,
        Long profileId,
        int score
) {}

package com.upc.viksadventuresapi.quiz.domain.model.commands;

public record CreateQuizCommand(
        String title,
        String description
) {}
package com.upc.viksadventuresapi.quiz.domain.model.valueobjects;

public record Score(int score) {
    public Score {
        if (score < 0) {
            throw new IllegalArgumentException("Score can't be negative");
        }
    }
}

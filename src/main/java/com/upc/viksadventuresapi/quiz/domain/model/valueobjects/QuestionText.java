package com.upc.viksadventuresapi.quiz.domain.model.valueobjects;

public record QuestionText(String questionText) {
    public QuestionText() {
        this(null);
    }

    public QuestionText {
        if (questionText == null || questionText.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
    }
}

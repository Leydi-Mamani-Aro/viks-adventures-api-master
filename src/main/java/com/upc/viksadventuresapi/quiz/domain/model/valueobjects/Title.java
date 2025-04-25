package com.upc.viksadventuresapi.quiz.domain.model.valueobjects;

public record Title(String title) {
    public Title() {
        this(null);
    }

    public Title {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
    }
}

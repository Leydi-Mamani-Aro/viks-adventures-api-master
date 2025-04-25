package com.upc.viksadventuresapi.quiz.domain.model.valueobjects;

public record Description(String description) {
    public Description() {
        this(null);
    }

    public Description {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
    }
}

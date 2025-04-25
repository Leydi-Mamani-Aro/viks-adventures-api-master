package com.upc.viksadventuresapi.quiz.domain.model.valueobjects;

public record ImageUrl(String imageUrl) {
    public ImageUrl() {
        this(null);
    }
    public ImageUrl {
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new IllegalArgumentException("ImageUrl cannot be null or empty");
        }
    }
}

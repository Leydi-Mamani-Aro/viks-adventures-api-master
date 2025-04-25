package com.upc.viksadventuresapi.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record BirthDate(String birthDate) {
    public BirthDate() {
        this(null);
    }

    public BirthDate {
        if (birthDate == null || birthDate.isBlank()) {
            throw new IllegalArgumentException("Birth date cannot be null or blank");
        }
    }
}

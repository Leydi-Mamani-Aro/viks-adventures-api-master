package com.upc.viksadventuresapi.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record School(String school) {
    public School() {
        this(null);
    }

    public School {
        if (school == null || school.isBlank()) {
            throw new IllegalArgumentException("School cannot be null or blank");
        }
    }
}

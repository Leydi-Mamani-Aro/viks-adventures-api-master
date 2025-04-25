package com.upc.viksadventuresapi.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record GradeLevel(String gradeLevel) {
    public GradeLevel() {
        this(null);
    }

    public GradeLevel {
        if (gradeLevel == null || gradeLevel.isBlank()) {
            throw new IllegalArgumentException("Grade level cannot be null or blank");
        }
    }
}

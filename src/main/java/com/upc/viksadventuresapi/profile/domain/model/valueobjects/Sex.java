package com.upc.viksadventuresapi.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Sex(String sex) {
    public Sex(){this(null);}

    // Cant be null
    public Sex {
        if (sex == null || sex.isBlank()) {
            throw new IllegalArgumentException("Sex cannot be null or blank");
        }
    }

}

package com.upc.viksadventuresapi.profile.interfaces.rest.resources;

public record CreateProfileResource(
        String firstName,
        String lastName,
        String birthDate,
        String sex,
        String gradeLevel,
        String school
        ) {
}

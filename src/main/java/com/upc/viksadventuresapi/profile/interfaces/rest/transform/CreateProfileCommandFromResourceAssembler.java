package com.upc.viksadventuresapi.profile.interfaces.rest.transform;

import com.upc.viksadventuresapi.profile.domain.model.commands.CreateProfileCommand;
import com.upc.viksadventuresapi.profile.interfaces.rest.resources.CreateProfileResource;

public class CreateProfileCommandFromResourceAssembler {
    public static CreateProfileCommand toCommandFromResource(CreateProfileResource resource, Long userId) {
        return new CreateProfileCommand(
                userId,
                resource.firstName(),
                resource.lastName(),
                resource.birthDate(),
                resource.sex(),
                resource.gradeLevel(),
                resource.school());
    }
}

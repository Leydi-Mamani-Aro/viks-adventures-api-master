package com.upc.viksadventuresapi.profile.interfaces.rest.transform;

import com.upc.viksadventuresapi.profile.domain.model.aggregates.Profile;
import com.upc.viksadventuresapi.profile.interfaces.rest.resources.ProfileResource;

public class ProfileResourceFromEntityAssembler {
    public static ProfileResource toResourceFromEntity(Profile entity) {
        return new ProfileResource(entity.getId(), entity.getUserId(), entity.getFullName(), entity.getBirthDate(), entity.getSex(), entity.getGradeLevel(), entity.getSchool());
    }
}

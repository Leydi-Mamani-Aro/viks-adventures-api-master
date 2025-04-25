package com.upc.viksadventuresapi.iam.interfaces.rest.transform;

import com.upc.viksadventuresapi.iam.domain.model.entities.Role;
import com.upc.viksadventuresapi.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(role.getId(), role.getStringName());
    }
}

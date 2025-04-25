package com.upc.viksadventuresapi.iam.domain.services;

import com.upc.viksadventuresapi.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}

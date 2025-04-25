package com.upc.viksadventuresapi.iam.domain.model.queries;

import com.upc.viksadventuresapi.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
}

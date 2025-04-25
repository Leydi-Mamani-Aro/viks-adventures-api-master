package com.upc.viksadventuresapi.iam.domain.services;

import com.upc.viksadventuresapi.iam.domain.model.aggregates.User;
import com.upc.viksadventuresapi.iam.domain.model.commands.DeleteUserByIdCommand;
import com.upc.viksadventuresapi.iam.domain.model.commands.SignInCommand;
import com.upc.viksadventuresapi.iam.domain.model.commands.SignUpCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
    Optional<User> handle(SignUpCommand command);
    // Delete user
    void  handle (DeleteUserByIdCommand command);
}

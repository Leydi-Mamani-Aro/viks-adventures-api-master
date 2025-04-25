package com.upc.viksadventuresapi.iam.interfaces.rest;

import com.upc.viksadventuresapi.iam.domain.model.commands.DeleteUserByIdCommand;
import com.upc.viksadventuresapi.iam.domain.model.queries.GetAllUsersQuery;
import com.upc.viksadventuresapi.iam.domain.model.queries.GetUserByIdQuery;
import com.upc.viksadventuresapi.iam.domain.services.UserCommandService;
import com.upc.viksadventuresapi.iam.domain.services.UserQueryService;
import com.upc.viksadventuresapi.iam.interfaces.rest.resources.UserResource;
import com.upc.viksadventuresapi.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is a REST controller that exposes the users resource.
 * It includes the following operations:
 * - GET /api/v1/users: returns all the users
 * - GET /api/v1/users/{userId}: returns the user with the given id
 **/
@RestController
@CrossOrigin(origins={"*"})
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    public UsersController(UserQueryService userQueryService, UserCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }

    /**
     * This method returns all the users.
     * @return a list of user resources
     * @see UserResource
     */
    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var getAllUsersQuery = new GetAllUsersQuery();
        var users = userQueryService.handle(getAllUsersQuery);
        var userResources = users.stream().map(UserResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(userResources);
    }

    /**
     * This method returns the user with the given id.
     * @param userId the user id
     * @return the user resource with the given id
     * @throws RuntimeException if the user is not found
     * @see UserResource
     */
    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUserByIdQuery);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        var deleteUserByIdCommand = new DeleteUserByIdCommand(userId);
        userCommandService.handle(deleteUserByIdCommand);
        return ResponseEntity.noContent().build();
    }
}

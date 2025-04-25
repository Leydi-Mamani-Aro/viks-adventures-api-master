package com.upc.viksadventuresapi.profile.interfaces.rest;

import com.upc.viksadventuresapi.iam.domain.model.aggregates.User;
import com.upc.viksadventuresapi.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.upc.viksadventuresapi.profile.domain.model.commands.DeleteProfileByIdCommand;
import com.upc.viksadventuresapi.profile.domain.model.queries.GetAllProfilesQuery;
import com.upc.viksadventuresapi.profile.domain.model.queries.GetProfileByIdQuery;
import com.upc.viksadventuresapi.profile.domain.services.ProfileCommandService;
import com.upc.viksadventuresapi.profile.domain.services.ProfileQueryService;
import com.upc.viksadventuresapi.profile.interfaces.rest.resources.CreateProfileResource;
import com.upc.viksadventuresapi.profile.interfaces.rest.resources.ProfileResource;
import com.upc.viksadventuresapi.profile.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import com.upc.viksadventuresapi.profile.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.stream.Collectors;

/**
 * ProfilesController
 * <p>
 *     This class is the entry point for all the REST endpoints related to the Profile entity.
 * </p>
 */
@RestController
@CrossOrigin(origins={"*"})
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profile Management Endpoints")
public class ProfilesController {
    private final ProfileQueryService profileQueryService;
    private final ProfileCommandService profileCommandService;
    private final UserRepository userRepository;

    public ProfilesController(ProfileQueryService profileQueryService, ProfileCommandService profileCommandService, UserRepository userRepository) {
        this.profileQueryService = profileQueryService;
        this.profileCommandService = profileCommandService;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new Profile
     * @param resource the resource containing the data to create the Profile
     * @return the created Profile
     */
    @PostMapping
    public ResponseEntity<ProfileResource> createProfile(
            @RequestBody CreateProfileResource resource,
            @AuthenticationPrincipal Jwt jwt) {

        String username = jwt.getSubject();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        Long userId = user.getId(); // ✅ este se guarda en la tabla profiles

        var createProfileCommand = CreateProfileCommandFromResourceAssembler.toCommandFromResource(resource, userId);
        var profile = profileCommandService.handle(createProfileCommand);
        if (profile.isEmpty()) return ResponseEntity.badRequest().build();

        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return new ResponseEntity<>(profileResource, HttpStatus.CREATED);
    }

    /**
     * Gets a Profile by its id
     * @param profileId the id of the Profile to get
     * @return the Profile resource associated to given Profile id
     */
    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long profileId) {
        var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) return ResponseEntity.badRequest().build();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    /**
     * Gets all the Profiles
     * @return a list of all the Profile resources currently stored
     */
    @GetMapping
    public ResponseEntity<List<ProfileResource>> getAllProfiles() {
        var getAllProfilesQuery = new GetAllProfilesQuery();
        var profiles = profileQueryService.handle(getAllProfilesQuery);
        var profileResources = profiles.stream().map(ProfileResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(profileResources);
    }

    /**
     * Deletes a Profile by its id
     * @param profileId the id of the Profile to delete
     * @return a response entity with no content
     */
    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfileById(@PathVariable Long profileId) {
        var deleteProfileByIdCommand = new DeleteProfileByIdCommand(profileId);
        profileCommandService.handle(deleteProfileByIdCommand);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<ProfileResource> getProfileByUsername(@PathVariable String username) {
        var profile = profileQueryService.findByUsername(username);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();

        var resource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(resource);
    }

}

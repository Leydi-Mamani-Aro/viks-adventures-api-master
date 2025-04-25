package com.upc.viksadventuresapi.profile.application.internal.commandservices;

import com.upc.viksadventuresapi.iam.domain.model.aggregates.User;
import com.upc.viksadventuresapi.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.upc.viksadventuresapi.profile.domain.model.aggregates.Profile;
import com.upc.viksadventuresapi.profile.domain.model.commands.CreateProfileCommand;
import com.upc.viksadventuresapi.profile.domain.model.commands.DeleteProfileByIdCommand;
import com.upc.viksadventuresapi.profile.domain.services.ProfileCommandService;
import com.upc.viksadventuresapi.profile.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Profile> handle(CreateProfileCommand command) {
        // Verify user exists
        Optional<User> optionalUser = userRepository.findById(command.userId());
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + command.userId() + " does not exist.");
        }
        User user = optionalUser.get();

        var profile = new Profile(user, command);
        profileRepository.save(profile);
        return Optional.of(profile);
    }

    @Override
    public void handle(DeleteProfileByIdCommand command) {
        if (!profileRepository.existsById(command.profileId())) {
            throw new IllegalArgumentException("Profile with ID " + command.profileId() + " does not exist.");
        }
        try {
            profileRepository.deleteById(command.profileId());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting profile with ID " + command.profileId());
        }
    }
}

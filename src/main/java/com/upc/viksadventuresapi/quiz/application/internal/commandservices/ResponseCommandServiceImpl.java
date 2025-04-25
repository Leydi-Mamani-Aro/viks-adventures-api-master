package com.upc.viksadventuresapi.quiz.application.internal.commandservices;

import com.upc.viksadventuresapi.profile.domain.model.aggregates.Profile;
import com.upc.viksadventuresapi.profile.infrastructure.persistence.jpa.repositories.ProfileRepository;
import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Option;
import com.upc.viksadventuresapi.quiz.domain.model.aggregates.Response;
import com.upc.viksadventuresapi.quiz.domain.model.commands.CreateResponseCommand;
import com.upc.viksadventuresapi.quiz.domain.model.commands.DeleteResponseByIdCommand;
import com.upc.viksadventuresapi.quiz.domain.model.events.ResponseCreatedEvent;
import com.upc.viksadventuresapi.quiz.domain.services.ResponseCommandService;
import com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories.OptionRepository;
import com.upc.viksadventuresapi.quiz.infrastructure.persistence.jpa.repositories.ResponseRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResponseCommandServiceImpl implements ResponseCommandService {
    private final ResponseRepository responseRepository;
    private final OptionRepository optionRepository;
    private final ProfileRepository profileRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ResponseCommandServiceImpl(ResponseRepository responseRepository, OptionRepository optionRepository, ProfileRepository profileRepository, ApplicationEventPublisher eventPublisher) {
        this.responseRepository = responseRepository;
        this.optionRepository = optionRepository;
        this.profileRepository = profileRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<Response> handle(CreateResponseCommand command) {
        // Verify if the option exists
        Option option = optionRepository.findById(command.optionId())
                .orElseThrow(() -> new IllegalArgumentException("Option with ID " + command.optionId() + " does not exist."));

        // Verify if the profile exists
        Profile profile = profileRepository.findById(command.profileId())
                .orElseThrow(() -> new IllegalArgumentException("Profile with ID " + command.profileId() + " does not exist."));

        // Create and save response
        Response response = new Response(option, profile);
        Response savedResponse = responseRepository.save(response);

        // Publish event
        eventPublisher.publishEvent(new ResponseCreatedEvent(this, savedResponse));

        return Optional.of(savedResponse);
    }

    @Override
    public void handle(DeleteResponseByIdCommand command) {
        if (!responseRepository.existsById(command.responseId())) {
            throw new IllegalArgumentException("Response with ID " + command.responseId() + " does not exist.");
        }
        try {
            responseRepository.deleteById(command.responseId());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting response with ID " + command.responseId());
        }
    }
}

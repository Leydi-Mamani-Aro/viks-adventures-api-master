package com.upc.viksadventuresapi.profile.application.internal.queryservices;

import com.upc.viksadventuresapi.profile.domain.model.aggregates.Profile;
import com.upc.viksadventuresapi.profile.domain.model.queries.GetAllProfilesQuery;
import com.upc.viksadventuresapi.profile.domain.model.queries.GetProfileByIdQuery;
import com.upc.viksadventuresapi.profile.domain.services.ProfileQueryService;
import com.upc.viksadventuresapi.profile.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {

    private final ProfileRepository profileRepository;

    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> handle(GetProfileByIdQuery query) {
        return profileRepository.findById(query.profileId());
    }

    @Override
    public List<Profile> handle(GetAllProfilesQuery query) {
        return profileRepository.findAll();
    }

    @Override
    public Optional<Profile> findByUsername(String username) {
        return profileRepository.findByUserUsername(username); // ✅ implementación correcta
    }
}
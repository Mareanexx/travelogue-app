package ru.mareanexx.feature_profiles.data.profile.remote.dto

import ru.mareanexx.feature_profiles.domain.trip.entity.Trip

data class AuthorsProfileResponse(
    val profile: ProfileDto,
    val trips: List<Trip>
)
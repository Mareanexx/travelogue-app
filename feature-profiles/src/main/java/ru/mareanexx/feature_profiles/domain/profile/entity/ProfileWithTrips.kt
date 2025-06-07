package ru.mareanexx.feature_profiles.domain.profile.entity

import ru.mareanexx.feature_profiles.data.profile.remote.dto.ProfileDto
import ru.mareanexx.feature_profiles.domain.trip.entity.Trip

data class ProfileWithTrips(
    val profile: ProfileDto,
    val trips: List<Trip>
)
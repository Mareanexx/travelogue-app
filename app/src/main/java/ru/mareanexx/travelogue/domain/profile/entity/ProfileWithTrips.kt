package ru.mareanexx.travelogue.domain.profile.entity

import ru.mareanexx.travelogue.data.profile.remote.dto.ProfileDto
import ru.mareanexx.travelogue.domain.trip.entity.Trip

data class ProfileWithTrips(
    val profile: ProfileDto,
    val trips: List<Trip>
)
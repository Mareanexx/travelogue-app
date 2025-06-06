package ru.mareanexx.travelogue.data.profile.remote.dto

import ru.mareanexx.travelogue.domain.trip.entity.Trip

data class AuthorsProfileResponse(
    val profile: ProfileDto,
    val trips: List<Trip>
)
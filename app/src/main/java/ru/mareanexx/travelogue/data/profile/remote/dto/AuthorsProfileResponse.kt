package ru.mareanexx.travelogue.data.profile.remote.dto

data class AuthorsProfileResponse(
    val profile: ProfileDto,
    val trips: List<AuthorTrip>
)
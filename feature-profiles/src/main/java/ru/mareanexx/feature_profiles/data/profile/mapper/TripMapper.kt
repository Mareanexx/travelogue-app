package ru.mareanexx.feature_profiles.data.profile.mapper

import ru.mareanexx.data.trip.entity.TripEntity
import ru.mareanexx.feature_profiles.domain.trip.entity.Trip

fun Trip.toEntity() = TripEntity(
    id = id,
    name = name,
    description = description,
    startDate = startDate,
    endDate = endDate,
    stepsNumber = stepsNumber,
    daysNumber = daysNumber,
    type = type,
    status = status,
    coverPhoto = coverPhoto,
    profileId = profileId
)
package ru.mareanexx.travelogue.data.trip.mapper

import ru.mareanexx.travelogue.data.profile.remote.dto.AuthorTrip
import ru.mareanexx.travelogue.data.trip.local.entity.TripEntity
import ru.mareanexx.travelogue.domain.trip.entity.Trip

fun TripEntity.toDto() = AuthorTrip(
    id = id,
    name = name,
    description = description,
    startDate = startDate,
    endDate = endDate,
    stepsNumber = stepsNumber,
    daysNumber = daysNumber,
    type = type,
    status = status,
    coverPhoto = coverPhoto
)

fun AuthorTrip.toEntity(profileId: Int) = TripEntity(
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
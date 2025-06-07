package ru.mareanexx.feature_profiles.data.trip.mapper

import ru.mareanexx.data.trip.dto.TripStats
import ru.mareanexx.data.trip.dto.TripStatsResponse
import ru.mareanexx.data.trip.entity.TripEntity
import ru.mareanexx.feature_profiles.data.trip.remote.dto.EditTripRequest
import ru.mareanexx.feature_profiles.data.trip.remote.dto.NewTripRequest
import ru.mareanexx.feature_profiles.domain.trip.entity.Trip
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.form.TripForm
import ru.mareanexx.network.data.tag.remote.dto.NewTagRequest
import ru.mareanexx.network.data.tag.remote.dto.NewTagResponse
import java.time.Instant
import java.time.OffsetDateTime
import java.time.Period
import java.time.ZoneId

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

fun TripEntity.toTrip(tagList: List<NewTagResponse>) = Trip(
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
    profileId = profileId,
    tagList = tagList,
)

fun TripForm.toRequest() = NewTripRequest(
    name = name,
    description = description,
    startDate = startDate,
    endDate = endDate,
    type = type,
    status = status,
    tagList = tagList.map { NewTagRequest(it) }
)

fun Trip.toForm() = TripForm(
    id = id,
    name = name,
    description = description,
    startDate = startDate,
    endDate = endDate,
    type = type,
    status = status,
    coverPhotoPath = coverPhoto,
    tagList = tagList?.map { it.name } ?: emptyList()
)

fun TripForm.toEditRequest() = EditTripRequest(
    id = id,
    name = name,
    description = description,
    startDate = startDate,
    endDate = endDate,
    type = type,
    status = status,
    tagList = tagList.map { NewTagRequest(it) }
)

fun TripStatsResponse.toStats(): TripStats {
    val localStartDate = Instant.ofEpochMilli(startDate.toLong())
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val days = maxArrivalDate?.let {
        val arrivalDate = OffsetDateTime.parse(it).toLocalDate()
        Period.between(localStartDate, arrivalDate).days + 1
    } ?: 0

    return TripStats(
        id = id,
        stepsNumber = stepsNumber,
        daysNumber = days
    )
}
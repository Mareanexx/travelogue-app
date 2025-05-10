package ru.mareanexx.travelogue.data.trip.mapper

import ru.mareanexx.travelogue.data.profile.remote.dto.AuthorTrip
import ru.mareanexx.travelogue.data.tag.remote.dto.NewTagRequest
import ru.mareanexx.travelogue.data.tag.remote.dto.NewTagResponse
import ru.mareanexx.travelogue.data.trip.local.entity.TripEntity
import ru.mareanexx.travelogue.data.trip.remote.dto.EditTripRequest
import ru.mareanexx.travelogue.data.trip.remote.dto.NewTripRequest
import ru.mareanexx.travelogue.domain.trip.entity.Trip
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.form.TripForm

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
    tagList = tagList?.map { it.name } ?: emptyList(),
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
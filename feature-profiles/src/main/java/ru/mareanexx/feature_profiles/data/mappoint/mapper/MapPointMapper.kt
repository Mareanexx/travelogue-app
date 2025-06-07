package ru.mareanexx.feature_profiles.data.mappoint.mapper

import ru.mareanexx.data.mappoint.entity.MapPointEntity
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.EditMapPointRequest
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.feature_profiles.domain.mappoint.entity.MapPoint
import ru.mareanexx.feature_profiles.presentation.screens.trip.viewmodel.form.MapPointForm
import java.time.OffsetDateTime

fun MapPointEntity.toResponse() = MapPoint(
    id = id,
    longitude = longitude,
    latitude = latitude,
    name = name,
    description = description,
    likesNumber = likesNumber,
    commentsNumber = commentsNumber,
    photosNumber = photosNumber,
    arrivalDate = arrivalDate,
    tripId = tripId
)

fun MapPoint.toEntity() = MapPointEntity(
    id = id,
    longitude = longitude,
    latitude = latitude,
    name = name,
    description = description,
    likesNumber = likesNumber,
    commentsNumber = commentsNumber,
    photosNumber = photosNumber,
    arrivalDate = arrivalDate,
    tripId = tripId
)

fun MapPointForm.toNewRequest(tripId: Int) =
    ru.mareanexx.feature_profiles.data.mappoint.remote.dto.NewMapPointRequest(
        longitude = longitude,
        latitude = latitude,
        name = name,
        description = description,
        arrivalDate = OffsetDateTime.of(arrivalDate, arrivalTime.toLocalTime(), arrivalTime.offset),
        tripId = tripId
    )

fun MapPointForm.toEditRequest() = EditMapPointRequest(
    id = id,
    longitude = longitude,
    latitude = latitude,
    name = name,
    description = description,
    arrivalDate = OffsetDateTime.of(arrivalDate, arrivalTime.toLocalTime(), arrivalTime.offset),
)

fun MapPointWithPhotos.toForm() = MapPointForm(
    id = mapPoint.id,
    longitude = mapPoint.longitude,
    latitude = mapPoint.latitude,
    name = mapPoint.name,
    description = mapPoint.description,
    arrivalDate = mapPoint.arrivalDate.toLocalDate(),
    arrivalTime = mapPoint.arrivalDate.toOffsetTime(),
    serverPhotos = photos,
    buttonEnabled = true
)
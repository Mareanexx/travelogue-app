package ru.mareanexx.feature_profiles.data.mappoint.remote.dto

import ru.mareanexx.data.trip.type.TripTimeStatus
import java.time.LocalDate
import java.time.OffsetDateTime

data class ActivityTripWithMapPoints(
    val id: Int,
    val name: String,
    val startDate: LocalDate,
    val stepsNumber: Int,
    val daysNumber: Int,
    val status: TripTimeStatus,
    val coverPhoto: String,

    val profileId: Int,
    val username: String,
    val avatar: String?,

    val mapPoints: List<MapPointWithPhotoActivity>
)

data class MapPointWithPhotoActivity(
    val id: Int,
    val longitude: Double,
    val latitude: Double,
    val arrivalDate: OffsetDateTime,
    val tripId: Int,
    val previewPhotoPath: String
)
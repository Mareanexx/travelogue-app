package ru.mareanexx.travelogue.data.profile.remote.dto

import ru.mareanexx.travelogue.data.trip.local.type.TripTimeStatus
import ru.mareanexx.travelogue.data.trip.local.type.TripVisibilityType
import java.time.LocalDate

data class AuthorTrip(
    val id: Int,
    val name: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val stepsNumber: Int,
    val daysNumber: Int,
    val type: TripVisibilityType,
    val status: TripTimeStatus,
    val coverPhoto: String
)
package ru.mareanexx.feature_explore.domain.entity

import ru.mareanexx.data.trip.type.TripTimeStatus
import java.time.LocalDate

data class TrendingTrip(
    val id: Int,
    val name: String,
    val startDate: LocalDate,
    val stepsNumber: Int,
    val daysNumber: Int,
    val status: TripTimeStatus,
    val coverPhoto: String,

    val profileId: Int,
    val username: String,
    val avatar: String?
)
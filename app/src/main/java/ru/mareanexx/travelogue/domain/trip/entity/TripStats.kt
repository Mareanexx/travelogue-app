package ru.mareanexx.travelogue.domain.trip.entity

data class TripStats(
    val id: Int,
    val stepsNumber: Int,
    val daysNumber: Int
)

data class TripStatsResponse(
    val id: Int,
    val startDate: String,
    val stepsNumber: Int,
    val maxArrivalDate: String?
)
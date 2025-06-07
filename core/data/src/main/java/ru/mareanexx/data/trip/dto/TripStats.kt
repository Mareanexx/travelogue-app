package ru.mareanexx.data.trip.dto

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
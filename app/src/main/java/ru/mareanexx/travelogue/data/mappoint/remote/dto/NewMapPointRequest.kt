package ru.mareanexx.travelogue.data.mappoint.remote.dto

import java.time.OffsetDateTime

data class NewMapPointRequest(
    val longitude: Double,
    val latitude: Double,
    val name: String,
    val description: String,
    val arrivalDate: OffsetDateTime,
    val tripId: Int
)
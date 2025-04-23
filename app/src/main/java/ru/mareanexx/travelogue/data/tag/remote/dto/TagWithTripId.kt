package ru.mareanexx.travelogue.data.tag.remote.dto

data class TagWithTripId(
    val id: Int,
    val name: String,
    val tripId: Int
)
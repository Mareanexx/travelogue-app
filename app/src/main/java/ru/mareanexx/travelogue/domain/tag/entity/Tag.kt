package ru.mareanexx.travelogue.domain.tag.entity

data class Tag(
    val id: Int,
    val tripId: Int,
    val name: String
)
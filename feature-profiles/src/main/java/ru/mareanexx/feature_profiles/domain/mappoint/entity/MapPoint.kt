package ru.mareanexx.feature_profiles.domain.mappoint.entity

import java.time.OffsetDateTime

data class MapPoint(
    val id: Int,
    val longitude: Double,
    val latitude: Double,
    val name: String,
    val description: String,
    val likesNumber: Int,
    val commentsNumber: Int,
    val photosNumber: Int,
    val arrivalDate: OffsetDateTime,
    val tripId: Int,
    val isLiked: Boolean = false
)
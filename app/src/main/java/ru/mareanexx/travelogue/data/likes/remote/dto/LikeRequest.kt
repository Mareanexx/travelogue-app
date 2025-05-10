package ru.mareanexx.travelogue.data.likes.remote.dto

data class LikeRequest(
    val profileId: Int,
    val mapPointId: Int
)
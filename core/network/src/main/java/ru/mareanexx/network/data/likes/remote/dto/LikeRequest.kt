package ru.mareanexx.network.data.likes.remote.dto

data class LikeRequest(
    val profileId: Int,
    val mapPointId: Int
)
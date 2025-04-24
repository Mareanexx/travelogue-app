package ru.mareanexx.travelogue.data.profile.remote.dto

data class UpdatedProfileStatsResponse(
    val tripsNumber: Int,
    val followersNumber: Int,
    val followingNumber: Int
)
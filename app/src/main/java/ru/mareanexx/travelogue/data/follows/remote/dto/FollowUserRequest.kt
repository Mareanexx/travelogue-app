package ru.mareanexx.travelogue.data.follows.remote.dto

data class FollowUserRequest(
    val followerId: Int,
    val followingId: Int
)
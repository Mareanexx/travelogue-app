package ru.mareanexx.network.data.follows.remote.dto

data class FollowUserRequest(
    val followerId: Int,
    val followingId: Int
)
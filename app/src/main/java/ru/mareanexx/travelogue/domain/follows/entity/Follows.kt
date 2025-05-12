package ru.mareanexx.travelogue.domain.follows.entity

data class Follows(
    val id: Int,
    val username: String,
    val avatar: String?,
    val bio: String,
    val isFollowing: Boolean
)
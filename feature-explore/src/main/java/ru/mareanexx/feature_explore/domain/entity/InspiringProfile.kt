package ru.mareanexx.feature_explore.domain.entity

data class InspiringProfile(
    val id: Int,
    val username: String,
    val bio: String,
    val avatar: String?,
    val coverPhoto: String?,
    val followersNumber: Int,
    val followingNumber: Int,
    val tripsNumber: Int,
    val isFollowing: Boolean
)
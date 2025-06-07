package ru.mareanexx.feature_profiles.data.profile.remote.dto

data class ProfileDto(
    val id: Int,
    val username: String,
    val fullName: String,
    val bio: String,
    val avatar: String?,
    val coverPhoto: String?,
    val followersNumber: Int,
    val followingNumber: Int,
    val tripsNumber: Int,
    val isFollowing: Boolean = false
)
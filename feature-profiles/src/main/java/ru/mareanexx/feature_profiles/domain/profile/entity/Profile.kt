package ru.mareanexx.feature_profiles.domain.profile.entity

import java.util.UUID

data class Profile(
    val id: Int,
    val username: String,
    val fullName: String,
    val bio: String,
    val avatar: String?,
    val coverPhoto: String? = null,
    val followersNumber: Int,
    val followingNumber: Int,
    val tripsNumber: Int,
    val userUUID: UUID,
    val fcmToken: String? = null
)
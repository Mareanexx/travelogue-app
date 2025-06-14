package ru.mareanexx.feature_auth.data.profile.remote.dto

import java.util.UUID

data class NewProfileRequest(
    val username: String,
    val fullName: String,
    val bio: String,
    var userUUID: UUID? = null
)
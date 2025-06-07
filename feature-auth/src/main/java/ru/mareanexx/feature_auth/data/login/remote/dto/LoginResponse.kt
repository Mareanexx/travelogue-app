package ru.mareanexx.feature_auth.data.login.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class LoginResponse(
    @SerializedName("userUuid") val userUuid: UUID,
    @SerializedName("email") val email: String,
    @SerializedName("token") val token: String
)
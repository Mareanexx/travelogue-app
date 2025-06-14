package ru.mareanexx.feature_auth.data.register.remote.dto

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
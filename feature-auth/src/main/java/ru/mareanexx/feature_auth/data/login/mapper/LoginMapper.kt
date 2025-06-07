package ru.mareanexx.feature_auth.data.login.mapper

import ru.mareanexx.feature_auth.data.login.remote.dto.LoginResponse
import ru.mareanexx.feature_auth.domain.login.entity.Login

fun LoginResponse.mapToEntity() = Login(
    userUuid = userUuid,
    email = email,
    token = token
)
package ru.mareanexx.feature_auth.data.register.mapper

import ru.mareanexx.feature_auth.data.register.remote.dto.RegisterResponse
import ru.mareanexx.feature_auth.domain.register.entity.Register

fun RegisterResponse.mapToEntity() = Register(
    email = email,
    userUuid = userUuid,
    token = token
)
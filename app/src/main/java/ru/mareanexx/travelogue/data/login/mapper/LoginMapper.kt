package ru.mareanexx.travelogue.data.login.mapper

import ru.mareanexx.travelogue.data.login.remote.dto.LoginResponse
import ru.mareanexx.travelogue.domain.login.entity.LoginEntity

fun LoginResponse.mapToEntity() = LoginEntity(
    userUuid = userUuid,
    email = email,
    token = token
)
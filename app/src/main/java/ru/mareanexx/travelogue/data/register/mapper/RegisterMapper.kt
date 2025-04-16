package ru.mareanexx.travelogue.data.register.mapper

import ru.mareanexx.travelogue.data.register.remote.dto.RegisterResponse
import ru.mareanexx.travelogue.domain.register.entity.RegisterEntity

fun RegisterResponse.mapToEntity() = RegisterEntity(
    email = email,
    userUuid = userUuid,
    token = token
)
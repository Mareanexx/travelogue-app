package ru.mareanexx.travelogue.data.register.mapper

import ru.mareanexx.travelogue.data.register.remote.dto.RegisterResponse
import ru.mareanexx.travelogue.domain.register.entity.Register

fun RegisterResponse.mapToEntity() = Register(
    email = email,
    userUuid = userUuid,
    token = token
)
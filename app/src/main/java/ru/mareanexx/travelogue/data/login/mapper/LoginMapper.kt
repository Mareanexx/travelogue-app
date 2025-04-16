package ru.mareanexx.travelogue.data.login.mapper

import ru.mareanexx.travelogue.data.login.remote.dto.LoginResponse
import ru.mareanexx.travelogue.domain.login.entity.Login

fun LoginResponse.mapToEntity() = Login(
    userUuid = userUuid,
    email = email,
    token = token
)
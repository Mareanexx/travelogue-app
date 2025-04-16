package ru.mareanexx.travelogue.domain.login.entity

import ru.mareanexx.travelogue.domain.common.AuthEntity
import java.util.UUID

data class LoginEntity(
    override val userUuid: UUID,
    override val email: String,
    override val token: String
) : AuthEntity
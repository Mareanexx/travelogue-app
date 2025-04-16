package ru.mareanexx.travelogue.domain.register.entity

import ru.mareanexx.travelogue.domain.common.AuthEntity
import java.util.UUID

data class RegisterEntity(
    override val userUuid: UUID,
    override val email: String,
    override val token: String
) : AuthEntity
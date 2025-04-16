package ru.mareanexx.travelogue.domain.register.entity

import ru.mareanexx.travelogue.domain.common.Auth
import java.util.UUID

data class Register(
    override val userUuid: UUID,
    override val email: String,
    override val token: String
) : Auth
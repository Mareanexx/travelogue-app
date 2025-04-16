package ru.mareanexx.travelogue.domain.login.entity

import ru.mareanexx.travelogue.domain.common.Auth
import java.util.UUID

data class Login(
    override val userUuid: UUID,
    override val email: String,
    override val token: String
) : Auth
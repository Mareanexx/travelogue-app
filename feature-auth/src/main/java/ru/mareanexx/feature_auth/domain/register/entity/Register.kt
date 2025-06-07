package ru.mareanexx.feature_auth.domain.register.entity

import ru.mareanexx.feature_auth.domain.login.entity.Auth
import java.util.UUID

data class Register(
    override val userUuid: UUID,
    override val email: String,
    override val token: String
) : Auth
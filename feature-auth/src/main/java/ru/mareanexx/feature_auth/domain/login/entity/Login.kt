package ru.mareanexx.feature_auth.domain.login.entity

import java.util.UUID

data class Login(
    override val userUuid: UUID,
    override val email: String,
    override val token: String
) : Auth
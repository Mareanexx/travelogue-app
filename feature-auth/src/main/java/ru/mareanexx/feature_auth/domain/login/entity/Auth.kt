package ru.mareanexx.feature_auth.domain.login.entity

import java.util.UUID

interface Auth {
    val userUuid: UUID
    val email: String
    val token: String
}
package ru.mareanexx.travelogue.domain.common

import java.util.UUID

interface AuthEntity {
    val userUuid: UUID
    val email: String
    val token: String
}
package ru.mareanexx.travelogue.domain.common

import java.util.UUID

interface Auth {
    val userUuid: UUID
    val email: String
    val token: String
}
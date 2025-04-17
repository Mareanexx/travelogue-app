package ru.mareanexx.travelogue.utils

import java.util.UUID
import javax.inject.Inject

class UserSessionManager @Inject constructor(
    private val dataStore: DataStore
) {
    suspend fun saveSession(token: String, userUuid: UUID) {
        dataStore.saveToken(token)
        dataStore.saveUserUuid(userUuid)
    }

    suspend fun clearSession() {
        dataStore.saveToken("")
        // можно и userUuid чистить
    }

    suspend fun getSession(): UserSession {
        val token = dataStore.getToken()
        val uuidString = dataStore.getUserUuid()
        val uuid = uuidString?.let { UUID.fromString(it) }

        return UserSession(token = token, userUuid = uuid)
    }
}

data class UserSession(
    val token: String,
    val userUuid: UUID?
)
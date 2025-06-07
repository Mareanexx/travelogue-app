package ru.mareanexx.common.utils

import java.util.UUID
import javax.inject.Inject

class UserSessionManager @Inject constructor(
    private val dataStore: DataStore
) {
    suspend fun saveSession(token: String, userUuid: UUID, email: String) {
        dataStore.saveToken(token)
        dataStore.saveUserUuid(userUuid)
        dataStore.saveUserEmail(email)
    }

    suspend fun saveProfileId(value: Int) = dataStore.saveProfileId(value)

    suspend fun saveNewEmail(email: String) {
        dataStore.saveUserEmail(email)
    }

    suspend fun clearSession() {
        dataStore.clearAll()
    }

    suspend fun getSession(): UserSession {
        val token = dataStore.getToken()
        val uuidString = dataStore.getUserUuid()
        val uuid = uuidString?.let { UUID.fromString(it) }
        val email = dataStore.getUserEmail()
        val profileId = dataStore.getProfileId()

        return UserSession(token = token, userUuid = uuid, email = email, profileId = profileId)
    }

    suspend fun getProfileId(): Int = dataStore.getProfileId()
    suspend fun getUserEmail(): String = dataStore.getUserEmail()
    suspend fun getToken() = dataStore.getToken()
}

data class UserSession(
    val token: String,
    val userUuid: UUID?,
    val email: String,
    val profileId: Int
)
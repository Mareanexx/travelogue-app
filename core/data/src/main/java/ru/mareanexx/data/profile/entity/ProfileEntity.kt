package ru.mareanexx.data.profile.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey val id: Int,
    val username: String,
    val fullName: String,
    val bio: String,
    val avatar: String?,
    val coverPhoto: String? = null,
    val followersNumber: Int,
    val followingNumber: Int,
    val tripsNumber: Int,
    val userUUID: UUID,
    val fcmToken: String? = null
)
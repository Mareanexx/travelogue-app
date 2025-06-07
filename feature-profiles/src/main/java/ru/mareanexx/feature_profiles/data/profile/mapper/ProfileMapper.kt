package ru.mareanexx.feature_profiles.data.profile.mapper

import ru.mareanexx.data.profile.entity.ProfileEntity
import ru.mareanexx.feature_profiles.data.profile.remote.dto.ProfileDto
import ru.mareanexx.feature_profiles.data.profile.remote.dto.UpdatedProfileStatsResponse
import ru.mareanexx.feature_profiles.domain.profile.entity.Profile
import java.util.UUID

fun ProfileEntity.toDto() = ProfileDto(
    id = id,
    username = username,
    fullName = fullName,
    bio = bio,
    avatar = avatar,
    coverPhoto = coverPhoto,
    followersNumber = followersNumber,
    followingNumber = followingNumber,
    tripsNumber = tripsNumber
)

fun ProfileDto.toEntity(userUuid: UUID) = ProfileEntity(
    id = id,
    username = username,
    fullName = fullName,
    bio = bio,
    avatar = avatar,
    coverPhoto = coverPhoto,
    followersNumber = followersNumber,
    followingNumber = followingNumber,
    tripsNumber = tripsNumber,
    userUUID = userUuid
)

fun Profile.toDto() = ProfileDto(
    id = id,
    username = username,
    fullName = fullName,
    bio = bio,
    avatar = avatar,
    coverPhoto = coverPhoto,
    followersNumber = followersNumber,
    followingNumber = followingNumber,
    tripsNumber = tripsNumber
)

fun Profile.toEntity() = ProfileEntity(
    id = id,
    username = username,
    fullName = fullName,
    bio = bio,
    avatar = avatar,
    coverPhoto = coverPhoto,
    followersNumber = followersNumber,
    followingNumber = followingNumber,
    tripsNumber = tripsNumber,
    userUUID = userUUID,
    fcmToken = fcmToken
)

fun ProfileDto?.copyStats(updatedStats: UpdatedProfileStatsResponse) = this?.copy(
    followersNumber = updatedStats.followersNumber,
    followingNumber = updatedStats.followingNumber,
    tripsNumber = updatedStats.tripsNumber
)
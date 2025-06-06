package ru.mareanexx.travelogue.data.profile.mapper

import ru.mareanexx.travelogue.data.profile.local.entity.ProfileEntity
import ru.mareanexx.travelogue.data.profile.remote.dto.AuthorCommentSender
import ru.mareanexx.travelogue.data.profile.remote.dto.NewProfileRequest
import ru.mareanexx.travelogue.data.profile.remote.dto.ProfileDto
import ru.mareanexx.travelogue.data.profile.remote.dto.UpdatedProfileStatsResponse
import ru.mareanexx.travelogue.domain.profile.entity.Profile
import ru.mareanexx.travelogue.presentation.screens.start.viewmodel.state.CreateProfileForm
import java.util.UUID

fun Profile.toEntity(): ProfileEntity = ProfileEntity(
    id = id,
    username = username,
    fullName = fullName,
    bio = bio,
    avatar = avatar,
    followersNumber = followersNumber,
    followingNumber = followingNumber,
    tripsNumber = tripsNumber,
    userUUID = userUUID
)

fun CreateProfileForm.toRequest() = NewProfileRequest(
    username = username,
    fullName = fullname,
    bio = bio
)

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

fun ProfileDto?.copyStats(updatedStats: UpdatedProfileStatsResponse) = this?.copy(
    followersNumber = updatedStats.followersNumber,
    followingNumber = updatedStats.followingNumber,
    tripsNumber = updatedStats.tripsNumber
)

fun ProfileEntity.toCommentSender() = AuthorCommentSender(
    id = id,
    username = username,
    avatar = avatar
)
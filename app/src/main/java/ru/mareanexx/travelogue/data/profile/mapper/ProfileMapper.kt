package ru.mareanexx.travelogue.data.profile.mapper

import ru.mareanexx.travelogue.data.profile.local.entity.ProfileEntity
import ru.mareanexx.travelogue.data.profile.remote.dto.NewProfileRequest
import ru.mareanexx.travelogue.domain.profile.entity.Profile
import ru.mareanexx.travelogue.presentation.screens.start.viewmodel.state.CreateProfileForm

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
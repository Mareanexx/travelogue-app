package ru.mareanexx.feature_auth.data.profile.mapper

import ru.mareanexx.data.profile.entity.ProfileEntity
import ru.mareanexx.feature_auth.data.profile.remote.dto.NewProfileRequest
import ru.mareanexx.feature_auth.presentation.screens.create_profile.viewmodel.form.CreateProfileForm
import ru.mareanexx.feature_auth.domain.profile.entity.Profile

fun Profile.toEntity(): ProfileEntity =
    ProfileEntity(
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
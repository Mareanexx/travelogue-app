package ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.form

import java.io.File

data class UpdateProfileForm(
    val username: String = "",
    val fullName: String = "",
    val bio: String = "",

    val avatar: File? = null,
    val wasAvatarReuploaded: Boolean = false,
    val newCoverPhoto: File? = null,
    val wasCoverReuploaded: Boolean = false,

    val buttonEnabled: Boolean = false,

    val isUsernameValid: Boolean = false,
    val isFullnameValid: Boolean = false
)

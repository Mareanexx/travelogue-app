package ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.state

import java.io.File

data class UpdateProfileForm(
    val username: String = "",
    val fullName: String = "",
    val bio: String = "",

    val avatar: File? = null,
    val coverPhoto: File? = null,

    val buttonEnabled: Boolean = false,

    val isUsernameValid: Boolean = false,
    val isFullnameValid: Boolean = false
)

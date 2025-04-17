package ru.mareanexx.travelogue.presentation.screens.start.viewmodel.state

import java.io.File

data class CreateProfileForm(
    val avatar: File? = null,
    val username: String = "",
    val fullname: String = "",
    val bio: String = "",

    val isUsernameValid: Boolean = false,
    val isFullnameValid: Boolean = false,
    val buttonEnabled: Boolean = false
)
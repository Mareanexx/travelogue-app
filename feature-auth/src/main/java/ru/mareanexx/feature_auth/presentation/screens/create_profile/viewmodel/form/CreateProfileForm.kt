package ru.mareanexx.feature_auth.presentation.screens.create_profile.viewmodel.form

import java.io.File

data class CreateProfileForm(
    val avatar: File? = null,
    val username: String = "",
    val fullname: String = "",
    val bio: String = "",

    val fieldsError: Map<ProfileField, ValidationErrorType> = emptyMap()
)
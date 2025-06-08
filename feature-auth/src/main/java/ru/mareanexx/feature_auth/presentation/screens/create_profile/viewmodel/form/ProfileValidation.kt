package ru.mareanexx.feature_auth.presentation.screens.create_profile.viewmodel.form

import androidx.annotation.StringRes

enum class ProfileField {
    Username, Fullname
}

enum class ValidationErrorType(@StringRes val errorText: Int) {
    TOO_SHORT_USERNAME(ru.mareanexx.core.common.R.string.username_requirement),
    TOO_SHORT_FULLNAME(ru.mareanexx.core.common.R.string.fullname_requirement)
}
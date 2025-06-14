package ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.event

import androidx.annotation.StringRes
import ru.mareanexx.feature_profiles.R

enum class DeletedType(@StringRes val text: Int) {
    Avatar(R.string.your_avatar_variant_del),
    Cover(R.string.your_cover_variant_del)
}

enum class TripTypifiedDialog {
    Delete, CreateTag
}
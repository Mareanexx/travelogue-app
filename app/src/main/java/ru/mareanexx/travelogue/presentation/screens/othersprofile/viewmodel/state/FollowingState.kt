package ru.mareanexx.travelogue.presentation.screens.othersprofile.viewmodel.state

data class FollowingState(
    val isAuthorFollowing: Boolean = false,
    val followersCounter: Int = 0
)
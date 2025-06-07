package ru.mareanexx.network.data.follows.remote.dto

import ru.mareanexx.network.domain.follows.entity.Follows

data class FollowersAndFollowingsResponse(
    val followers: List<Follows>,
    val followings: List<Follows>
)
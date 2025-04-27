package ru.mareanexx.travelogue.data.follows.remote.dto

import ru.mareanexx.travelogue.domain.follows.entity.Follows

data class FollowersAndFollowingsResponse(
    val followers: List<Follows>,
    val followings: List<Follows>
)
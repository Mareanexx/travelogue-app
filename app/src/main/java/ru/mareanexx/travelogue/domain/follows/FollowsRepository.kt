package ru.mareanexx.travelogue.domain.follows

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.follows.remote.dto.FollowersAndFollowingsResponse
import ru.mareanexx.travelogue.domain.common.BaseResult

interface FollowsRepository {
    suspend fun getFollowersAndFollowings(profileId: String): Flow<BaseResult<FollowersAndFollowingsResponse, String>>
    suspend fun followUser(followingId: Int): Flow<BaseResult<String, String>>
    suspend fun unfollowUser(followingId: Int): Flow<BaseResult<String, String>>
}
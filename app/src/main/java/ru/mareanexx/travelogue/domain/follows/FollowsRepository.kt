package ru.mareanexx.travelogue.domain.follows

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.data.follows.remote.dto.FollowUserRequest
import ru.mareanexx.travelogue.data.follows.remote.dto.FollowersAndFollowingsResponse
import ru.mareanexx.travelogue.domain.common.BaseResult

interface FollowsRepository {
    suspend fun getFollowersAndFollowings(profileId: Int): Flow<BaseResult<FollowersAndFollowingsResponse, String>>
    suspend fun followUser(followUserRequest: FollowUserRequest): Flow<BaseResult<String, String>>
    suspend fun unfollowUser(followUserRequest: FollowUserRequest): Flow<BaseResult<String, String>>
}
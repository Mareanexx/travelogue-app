package ru.mareanexx.network.domain.follows

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.data.follows.remote.dto.FollowersAndFollowingsResponse
import ru.mareanexx.network.utils.data.BaseResult

interface FollowsRepository {
    suspend fun getFollowersAndFollowings(profileId: String): Flow<BaseResult<FollowersAndFollowingsResponse, String>>
    suspend fun followUser(followingId: Int): Flow<BaseResult<String, String>>
    suspend fun unfollowUser(followingId: Int): Flow<BaseResult<String, String>>
}
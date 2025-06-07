package ru.mareanexx.network.data.follows.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.network.data.follows.remote.api.FollowsApi
import ru.mareanexx.network.data.follows.remote.dto.FollowUserRequest
import ru.mareanexx.network.data.follows.remote.dto.FollowersAndFollowingsResponse
import ru.mareanexx.network.domain.follows.FollowsRepository
import javax.inject.Inject

class FollowsRepositoryImpl @Inject constructor(
    private val followsApi: FollowsApi,
    private val userSessionManager: UserSessionManager
): FollowsRepository {
    override suspend fun getFollowersAndFollowings(profileId: String): Flow<BaseResult<FollowersAndFollowingsResponse, String>> {
        return flow {
            val authorId = userSessionManager.getProfileId()
            val othersProfileId = if (profileId == "me") userSessionManager.getProfileId() else profileId.toIntOrNull() ?: 0
            val response = followsApi.getFollowersAndFollowings(authorId = authorId, othersId = othersProfileId)
            if (response.isSuccessful) {
                val data = response.body()!!.data!!
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun followUser(followingId: Int): Flow<BaseResult<String, String>> {
        return flow {
            val authorId = userSessionManager.getProfileId()
            val response = followsApi.followUser(FollowUserRequest(authorId, followingId))
            if (response.isSuccessful) {
                val data = response.body()!!.message!!
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun unfollowUser(followingId: Int): Flow<BaseResult<String, String>> {
        return flow {
            val authorId = userSessionManager.getProfileId()
            val response = followsApi.unfollowUser(authorId, followingId)
            if (response.isSuccessful) {
                val data = response.body()!!.message!!
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }
}
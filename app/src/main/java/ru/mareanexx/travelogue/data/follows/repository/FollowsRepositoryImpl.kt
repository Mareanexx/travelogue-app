package ru.mareanexx.travelogue.data.follows.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mareanexx.travelogue.data.follows.remote.api.FollowsApi
import ru.mareanexx.travelogue.data.follows.remote.dto.FollowUserRequest
import ru.mareanexx.travelogue.data.follows.remote.dto.FollowersAndFollowingsResponse
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.follows.FollowsRepository
import javax.inject.Inject

class FollowsRepositoryImpl @Inject constructor(
    private val followsApi: FollowsApi
): FollowsRepository {
    override suspend fun getFollowersAndFollowings(profileId: Int): Flow<BaseResult<FollowersAndFollowingsResponse, String>> {
        return flow {
            val response = followsApi.getFollowersAndFollowings(profileId)
            if (response.isSuccessful) {
                val data = response.body()!!.data!!
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun followUser(followUserRequest: FollowUserRequest): Flow<BaseResult<String, String>> {
        return flow {
            val response = followsApi.followUser(followUserRequest)
            if (response.isSuccessful) {
                val data = response.body()!!.message!!
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun unfollowUser(followUserRequest: FollowUserRequest): Flow<BaseResult<String, String>> {
        return flow {
            val response = followsApi.unfollowUser(followUserRequest.followerId, followUserRequest.followingId)
            if (response.isSuccessful) {
                val data = response.body()!!.message!!
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }
}
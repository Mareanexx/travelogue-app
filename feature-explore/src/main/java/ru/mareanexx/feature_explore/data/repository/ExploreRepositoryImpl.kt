package ru.mareanexx.feature_explore.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.feature_explore.data.remote.api.ExploreApi
import ru.mareanexx.feature_explore.domain.ExploreRepository
import ru.mareanexx.feature_explore.domain.entity.InspiringProfile
import ru.mareanexx.feature_explore.domain.entity.SearchResult
import ru.mareanexx.feature_explore.domain.entity.TopTag
import ru.mareanexx.feature_explore.domain.entity.TrendingTrip
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

class ExploreRepositoryImpl @Inject constructor(
    private val userSessionManager: UserSessionManager,
    private val exploreApi: ExploreApi
): ExploreRepository {
    override suspend fun getTrendingTags(): Flow<BaseResult<List<TopTag>, String>> {
        return flow {
            val response = exploreApi.getTrendingTags()
            if (response.isSuccessful) {
                val data = response.body()!!.data!!
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun getTrendingTrips(): Flow<BaseResult<List<TrendingTrip>, String>> {
        return flow {
            val authorId = userSessionManager.getProfileId()
            val response = exploreApi.getTrendingTrips(authorId)
            if (response.isSuccessful) {
                val data = response.body()!!.data!!
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun getInspiringTravelers(): Flow<BaseResult<List<InspiringProfile>, String>> {
        return flow {
            val authorId = userSessionManager.getProfileId()
            val response = exploreApi.getInspiringTravelers(authorId)
            if (response.isSuccessful) {
                val data = response.body()!!.data!!
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun getTaggedTrips(tagName: String): Flow<BaseResult<List<TrendingTrip>, String>> {
        return flow {
            val profileId = userSessionManager.getProfileId()
            val response = exploreApi.getTripsByTag(finderId = profileId, tagName = tagName)
            if (response.isSuccessful) {
                emit(BaseResult.Success(response.body()!!.data!!))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun search(query: String): Flow<BaseResult<SearchResult, String>> {
        return flow {
            val authorId = userSessionManager.getProfileId()
            val response = exploreApi.search(authorId, query)
            if (response.isSuccessful) {
                val data = response.body()!!.data!!
                emit(BaseResult.Success(data))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }
}
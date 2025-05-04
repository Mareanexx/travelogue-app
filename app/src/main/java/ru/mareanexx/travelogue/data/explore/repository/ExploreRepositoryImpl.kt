package ru.mareanexx.travelogue.data.explore.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mareanexx.travelogue.data.explore.remote.api.ExploreApi
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.explore.ExploreRepository
import ru.mareanexx.travelogue.domain.explore.entity.InspiringProfile
import ru.mareanexx.travelogue.domain.explore.entity.SearchResult
import ru.mareanexx.travelogue.domain.explore.entity.TopTag
import ru.mareanexx.travelogue.domain.explore.entity.TrendingTrip
import ru.mareanexx.travelogue.utils.UserSessionManager
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
package ru.mareanexx.feature_explore.domain

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_explore.domain.entity.InspiringProfile
import ru.mareanexx.feature_explore.domain.entity.SearchResult
import ru.mareanexx.feature_explore.domain.entity.TopTag
import ru.mareanexx.feature_explore.domain.entity.TrendingTrip
import ru.mareanexx.network.utils.data.BaseResult

interface ExploreRepository {
    suspend fun getTrendingTags(): Flow<BaseResult<List<TopTag>, String>>
    suspend fun getTrendingTrips(): Flow<BaseResult<List<TrendingTrip>, String>>
    suspend fun getInspiringTravelers(): Flow<BaseResult<List<InspiringProfile>, String>>
    suspend fun getTaggedTrips(tagName: String) : Flow<BaseResult<List<TrendingTrip>, String>>
    suspend fun search(query: String): Flow<BaseResult<SearchResult, String>>
}
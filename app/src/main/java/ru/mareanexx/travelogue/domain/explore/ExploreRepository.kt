package ru.mareanexx.travelogue.domain.explore

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.explore.entity.InspiringProfile
import ru.mareanexx.travelogue.domain.explore.entity.SearchResult
import ru.mareanexx.travelogue.domain.explore.entity.TopTag
import ru.mareanexx.travelogue.domain.explore.entity.TrendingTrip

interface ExploreRepository {
    suspend fun getTrendingTags(): Flow<BaseResult<List<TopTag>, String>>
    suspend fun getTrendingTrips(): Flow<BaseResult<List<TrendingTrip>, String>>
    suspend fun getInspiringTravelers(): Flow<BaseResult<List<InspiringProfile>, String>>
    suspend fun search(query: String): Flow<BaseResult<SearchResult, String>>
}
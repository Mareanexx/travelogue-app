package ru.mareanexx.travelogue.data.explore.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.domain.explore.entity.InspiringProfileResponse
import ru.mareanexx.travelogue.domain.explore.entity.TopTag
import ru.mareanexx.travelogue.domain.explore.entity.TrendingTrip

interface ExploreApi {
    @GET("explore/inspiring")
    suspend fun getInspiringTravelers(@Query("authorId") authorId: Int): Response<WrappedResponse<List<InspiringProfileResponse>>>

    @GET("explore/tags")
    suspend fun getTrendingTags(): Response<WrappedResponse<List<TopTag>>>

    @GET("explore/trips")
    suspend fun getTrendingTrips(@Query("authorId") authorId: Int): Response<WrappedResponse<List<TrendingTrip>>>
}
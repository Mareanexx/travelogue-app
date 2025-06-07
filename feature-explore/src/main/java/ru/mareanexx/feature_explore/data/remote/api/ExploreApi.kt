package ru.mareanexx.feature_explore.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mareanexx.feature_explore.domain.entity.InspiringProfile
import ru.mareanexx.feature_explore.domain.entity.SearchResult
import ru.mareanexx.feature_explore.domain.entity.TopTag
import ru.mareanexx.feature_explore.domain.entity.TrendingTrip
import ru.mareanexx.network.utils.data.WrappedResponse

interface ExploreApi {
    @GET("explore/inspiring")
    suspend fun getInspiringTravelers(@Query("authorId") authorId: Int): Response<WrappedResponse<List<InspiringProfile>>>

    @GET("explore/tags")
    suspend fun getTrendingTags(): Response<WrappedResponse<List<TopTag>>>

    @GET("explore/trips")
    suspend fun getTrendingTrips(@Query("authorId") authorId: Int): Response<WrappedResponse<List<TrendingTrip>>>

    @GET("trips/tagged")
    suspend fun getTripsByTag(
        @Query("finderId") finderId: Int,
        @Query("tagName") tagName: String
    ) : Response<WrappedResponse<List<TrendingTrip>>>

    @GET("explore/search")
    suspend fun search(
        @Query("authorId") authorId: Int,
        @Query("query") query: String
    ): Response<WrappedResponse<SearchResult>>
}
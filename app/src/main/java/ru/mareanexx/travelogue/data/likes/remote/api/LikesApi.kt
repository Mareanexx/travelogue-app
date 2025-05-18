package ru.mareanexx.travelogue.data.likes.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.data.likes.remote.dto.LikeRequest

interface LikesApi {
    @POST("likes")
    suspend fun addNew(@Body likeRequest: LikeRequest) : Response<WrappedResponse<String>>

    @DELETE("likes")
    suspend fun deleteExisting(
        @Query("profileId") profileId: Int,
        @Query("mapPointId") mapPointId: Int
    ) : Response<WrappedResponse<String>>
}
package ru.mareanexx.network.data.follows.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.mareanexx.network.utils.data.WrappedResponse
import ru.mareanexx.network.data.follows.remote.dto.FollowUserRequest
import ru.mareanexx.network.data.follows.remote.dto.FollowersAndFollowingsResponse

interface FollowsApi {
    @GET("follows")
    suspend fun getFollowersAndFollowings(
        @Query("authorId") authorId: Int,
        @Query("othersId") othersId: Int
    ): Response<WrappedResponse<FollowersAndFollowingsResponse>>

    @POST("follows")
    suspend fun followUser(@Body followUserRequest: FollowUserRequest): Response<WrappedResponse<String>>

    @DELETE("follows")
    suspend fun unfollowUser(
        @Query("followerId") followerId: Int,
        @Query("followingId") followingId: Int
    ): Response<WrappedResponse<String>>
}
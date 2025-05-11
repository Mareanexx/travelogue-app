package ru.mareanexx.travelogue.data.trip.remote.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.data.trip.remote.dto.TripWithMapPoints
import ru.mareanexx.travelogue.domain.explore.entity.TrendingTrip
import ru.mareanexx.travelogue.domain.trip.entity.Trip

interface TripApi {
    @GET("trips")
    suspend fun getTripWithMapPoints(
        @Query("authorId") authorId: Int,
        @Query("tripId") tripId: Int
    ) : Response<WrappedResponse<TripWithMapPoints>>

    @Multipart
    @POST("trips")
    suspend fun addNewTrip(
        @Part("data") data: RequestBody,
        @Part cover: MultipartBody.Part
    ): Response<WrappedResponse<Trip>>

    @DELETE("trips/{tripId}")
    suspend fun deleteTrip(@Path("tripId") tripId: Int): Response<WrappedResponse<String>>

    @Multipart
    @PATCH("trips")
    suspend fun updateTrip(
        @Part("data") data: RequestBody,
        @Part cover: MultipartBody.Part?
    ): Response<WrappedResponse<Trip>>

    @GET("trips/tagged")
    suspend fun getTripsByTag(
        @Query("finderId") finderId: Int,
        @Query("tagName") tagName: String
    ) : Response<WrappedResponse<List<TrendingTrip>>>
}
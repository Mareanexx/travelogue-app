package ru.mareanexx.travelogue.data.trip.remote.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.domain.trip.entity.Trip

interface TripApi {
    @Multipart
    @POST("trips")
    suspend fun addNewTrip(
        @Part("data") data: RequestBody,
        @Part cover: MultipartBody.Part
    ): Response<WrappedResponse<Trip>>

    @DELETE("trips/{tripId}")
    suspend fun deleteTrip(@Path("tripId") tripId: Int): Response<String>

    @Multipart
    @PATCH
    suspend fun updateTrip(
        @Part("data") data: RequestBody,
        @Part cover: MultipartBody.Part?
    ): Response<WrappedResponse<Trip>>
}
package ru.mareanexx.feature_profiles.data.trip.remote.api

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
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.ActivityTripWithMapPoints
import ru.mareanexx.feature_profiles.data.trip.remote.dto.TripWithMapPoints
import ru.mareanexx.feature_profiles.domain.trip.entity.Trip
import ru.mareanexx.network.utils.data.WrappedResponse

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

    @GET("trips/activity")
    suspend fun getActivity(@Query("authorId") authorId: Int) : Response<WrappedResponse<List<ActivityTripWithMapPoints>>>
}
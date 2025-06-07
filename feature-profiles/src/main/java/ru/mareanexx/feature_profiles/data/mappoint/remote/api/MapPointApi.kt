package ru.mareanexx.feature_profiles.data.mappoint.remote.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.network.utils.data.WrappedResponse

interface MapPointApi {
    @Multipart
    @POST("map-points")
    suspend fun addNew(
        @Part("data") data: RequestBody,
        @Part photos: List<MultipartBody.Part>
    ): Response<WrappedResponse<MapPointWithPhotos>>

    @Multipart
    @PATCH("map-points")
    suspend fun update(
        @Part("data") data: RequestBody,
        @Part("deleted") deleted: RequestBody,
        @Part photos: List<MultipartBody.Part>
    ): Response<WrappedResponse<MapPointWithPhotos>>

    @DELETE("map-points/{mapPointId}")
    suspend fun delete(@Path("mapPointId") mapPointId: Int): Response<WrappedResponse<String>>
}
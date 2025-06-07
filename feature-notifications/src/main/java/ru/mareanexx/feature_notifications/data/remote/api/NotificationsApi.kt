package ru.mareanexx.feature_notifications.data.remote.api

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mareanexx.feature_notifications.domain.entity.Notification
import ru.mareanexx.network.utils.data.WrappedResponse

interface NotificationsApi {
    @GET("notifications")
    suspend fun getAll(@Query("profileId") profileId: Int): Response<WrappedResponse<List<Notification>>>

    @DELETE("notifications")
    suspend fun deleteAll(@Query("profileId") profileId: Int): Response<WrappedResponse<String>>
}
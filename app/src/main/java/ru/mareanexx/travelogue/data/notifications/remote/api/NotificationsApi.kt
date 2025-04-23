package ru.mareanexx.travelogue.data.notifications.remote.api

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.domain.notifications.entity.Notification

interface NotificationsApi {
    @GET("notifications")
    suspend fun getAll(@Query("profileId") profileId: Int): Response<WrappedResponse<List<Notification>>>

    @DELETE("notifications")
    suspend fun deleteAll(@Query("profileId") profileId: Int): Response<WrappedResponse<String>>
}
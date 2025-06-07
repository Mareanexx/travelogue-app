package ru.mareanexx.network.data.report.remote.api

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query
import ru.mareanexx.network.utils.data.WrappedResponse

interface ReportApi {
    @POST("reports")
    suspend fun add(@Query("tripId") tripId: Int): Response<WrappedResponse<String>>
}
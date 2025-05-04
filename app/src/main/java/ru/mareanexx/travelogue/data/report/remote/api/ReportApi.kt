package ru.mareanexx.travelogue.data.report.remote.api

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query
import ru.mareanexx.travelogue.data.common.WrappedResponse

interface ReportApi {
    @POST("reports")
    suspend fun add(@Query("tripId") tripId: Int): Response<WrappedResponse<String>>
}
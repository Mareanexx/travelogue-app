package ru.mareanexx.travelogue.data.comments.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.mareanexx.travelogue.data.comments.remote.dto.NewCommentRequest
import ru.mareanexx.travelogue.data.comments.remote.dto.NewCommentResponse
import ru.mareanexx.travelogue.data.common.WrappedResponse
import ru.mareanexx.travelogue.domain.comments.entity.Comment

interface CommentsApi {
    @GET("comments")
    suspend fun getAllByMapPointId(@Query("mapPointId") mapPointId: Int): Response<WrappedResponse<List<Comment>>>

    @POST("comments")
    suspend fun addNewComment(@Body newCommentRequest: NewCommentRequest): Response<WrappedResponse<NewCommentResponse>>
}
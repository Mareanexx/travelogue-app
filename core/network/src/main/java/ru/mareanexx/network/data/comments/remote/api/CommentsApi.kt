package ru.mareanexx.network.data.comments.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.mareanexx.network.data.comments.remote.dto.NewCommentRequest
import ru.mareanexx.network.data.comments.remote.dto.NewCommentResponse
import ru.mareanexx.network.domain.comments.entity.Comment
import ru.mareanexx.network.utils.data.WrappedResponse

interface CommentsApi {
    @GET("comments")
    suspend fun getAllByMapPointId(@Query("mapPointId") mapPointId: Int): Response<WrappedResponse<List<Comment>>>

    @POST("comments")
    suspend fun addNewComment(@Body newCommentRequest: NewCommentRequest): Response<WrappedResponse<NewCommentResponse>>
}
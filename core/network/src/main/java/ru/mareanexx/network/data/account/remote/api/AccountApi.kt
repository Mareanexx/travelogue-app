package ru.mareanexx.network.data.account.remote.api

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.Path
import ru.mareanexx.network.utils.data.WrappedResponse
import ru.mareanexx.network.data.account.remote.dto.NewEmailResponse
import java.util.UUID

interface AccountApi {
    @PATCH("{uuid}/email")
    suspend fun updateEmail(@Path("uuid") uuid: UUID): Response<WrappedResponse<NewEmailResponse>>

    @DELETE("me/{uuid}")
    suspend fun deleteAccount(@Path("uuid") uuid: UUID): Response<String>
}
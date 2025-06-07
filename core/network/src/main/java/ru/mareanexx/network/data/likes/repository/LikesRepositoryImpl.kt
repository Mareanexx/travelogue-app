package ru.mareanexx.network.data.likes.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.network.data.likes.remote.api.LikesApi
import ru.mareanexx.network.data.likes.remote.dto.LikeRequest
import ru.mareanexx.network.domain.likes.LikesRepository
import javax.inject.Inject

class LikesRepositoryImpl @Inject constructor(
    private val userSession: UserSessionManager,
    private val likesApi: LikesApi
): LikesRepository {

    override suspend fun addNew(mapPointId: Int): Flow<BaseResult<String, String>> {
        return flow {
            val senderId = userSession.getProfileId()
            val response = likesApi.addNew(LikeRequest(senderId, mapPointId))
            if (response.isSuccessful) {
                val message = response.body()!!.data!!
                emit(BaseResult.Success(message))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }

    override suspend fun deleteExisted(mapPointId: Int): Flow<BaseResult<String, String>> {
        return flow {
            val senderId = userSession.getProfileId()
            val response = likesApi.deleteExisting(senderId, mapPointId)
            if (response.isSuccessful) {
                emit(BaseResult.Success(response.body()!!.data!!))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }
}
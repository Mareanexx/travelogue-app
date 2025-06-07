package ru.mareanexx.network.domain.likes

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.utils.data.BaseResult

interface LikesRepository {
    suspend fun addNew(mapPointId: Int) : Flow<BaseResult<String, String>>
    suspend fun deleteExisted(mapPointId: Int) : Flow<BaseResult<String, String>>
}
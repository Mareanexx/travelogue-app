package ru.mareanexx.travelogue.domain.likes

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.common.BaseResult

interface LikesRepository {
    suspend fun addNew(mapPointId: Int) : Flow<BaseResult<String, String>>
    suspend fun deleteExisted(mapPointId: Int) : Flow<BaseResult<String, String>>
}
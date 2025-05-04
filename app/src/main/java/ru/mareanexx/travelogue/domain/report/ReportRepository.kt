package ru.mareanexx.travelogue.domain.report

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.common.BaseResult

interface ReportRepository {
    suspend fun add(tripId: Int): Flow<BaseResult<String, String>>
}
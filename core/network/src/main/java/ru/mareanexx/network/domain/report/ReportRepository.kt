package ru.mareanexx.network.domain.report

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.utils.data.BaseResult

interface ReportRepository {
    suspend fun add(tripId: Int): Flow<BaseResult<String, String>>
}
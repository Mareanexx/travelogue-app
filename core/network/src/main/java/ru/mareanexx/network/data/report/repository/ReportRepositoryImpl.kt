package ru.mareanexx.network.data.report.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.data.report.remote.api.ReportApi
import ru.mareanexx.network.domain.report.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportApi: ReportApi
): ReportRepository {
    override suspend fun add(tripId: Int): Flow<BaseResult<String, String>> {
        return flow {
            val response = reportApi.add(tripId)
            if (response.isSuccessful) {
                emit(BaseResult.Success(response.body()?.message ?: "nothing"))
            } else {
                emit(BaseResult.Error(response.body()?.message ?: "Unknown error"))
            }
        }
    }
}
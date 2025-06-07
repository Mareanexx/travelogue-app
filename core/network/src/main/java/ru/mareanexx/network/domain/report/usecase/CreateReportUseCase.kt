package ru.mareanexx.network.domain.report.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.network.utils.data.BaseResult
import ru.mareanexx.network.domain.report.ReportRepository
import javax.inject.Inject

class CreateReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(tripId: Int): Flow<BaseResult<String, String>> {
        return reportRepository.add(tripId)
    }
}
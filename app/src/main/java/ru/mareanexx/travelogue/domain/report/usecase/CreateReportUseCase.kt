package ru.mareanexx.travelogue.domain.report.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.report.ReportRepository
import javax.inject.Inject

class CreateReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(tripId: Int): Flow<BaseResult<String, String>> {
        return reportRepository.add(tripId)
    }
}
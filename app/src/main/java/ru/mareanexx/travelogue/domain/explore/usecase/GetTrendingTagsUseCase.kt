package ru.mareanexx.travelogue.domain.explore.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.explore.ExploreRepository
import ru.mareanexx.travelogue.domain.explore.entity.TopTag
import javax.inject.Inject

class GetTrendingTagsUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<List<TopTag>, String>> {
        return exploreRepository.getTrendingTags()
    }
}
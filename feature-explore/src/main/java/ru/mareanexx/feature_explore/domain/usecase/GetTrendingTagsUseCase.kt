package ru.mareanexx.feature_explore.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_explore.domain.ExploreRepository
import ru.mareanexx.feature_explore.domain.entity.TopTag
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

class GetTrendingTagsUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<List<TopTag>, String>> {
        return exploreRepository.getTrendingTags()
    }
}
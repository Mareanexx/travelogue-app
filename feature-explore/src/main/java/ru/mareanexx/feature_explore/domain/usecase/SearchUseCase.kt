package ru.mareanexx.feature_explore.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.feature_explore.domain.ExploreRepository
import ru.mareanexx.feature_explore.domain.entity.SearchResult
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository
) {
    suspend operator fun invoke(query: String): Flow<BaseResult<SearchResult, String>> {
        return exploreRepository.search(query)
    }
}
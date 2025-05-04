package ru.mareanexx.travelogue.domain.explore.usecase

import kotlinx.coroutines.flow.Flow
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.explore.ExploreRepository
import ru.mareanexx.travelogue.domain.explore.entity.SearchResult
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository
) {
    suspend operator fun invoke(query: String): Flow<BaseResult<SearchResult, String>> {
        return exploreRepository.search(query)
    }
}
package ru.mareanexx.travelogue.presentation.screens.explore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.explore.entity.InspiringProfileResponse
import ru.mareanexx.travelogue.domain.explore.entity.TopTag
import ru.mareanexx.travelogue.domain.explore.entity.TrendingTrip
import ru.mareanexx.travelogue.domain.explore.usecase.GetInspiringTravelersUseCase
import ru.mareanexx.travelogue.domain.explore.usecase.GetTrendingTagsUseCase
import ru.mareanexx.travelogue.domain.explore.usecase.GetTrendingTripsUseCase
import ru.mareanexx.travelogue.domain.follows.usecase.FollowUserUseCase
import ru.mareanexx.travelogue.domain.follows.usecase.UnfollowUserUseCase
import javax.inject.Inject

sealed class ExploreUiState {
    data object Loading : ExploreUiState()
    data class Error(val message: String) : ExploreUiState()
    data object Showing : ExploreUiState()
}

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getTrendingTagsUseCase: GetTrendingTagsUseCase,
    private val getTrendingTripsUseCase: GetTrendingTripsUseCase,
    private val getInspiringTravelersUseCase: GetInspiringTravelersUseCase,
    private val followUserUseCase: FollowUserUseCase,
    private val unfollowUserUseCase: UnfollowUserUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<ExploreUiState>(ExploreUiState.Loading)
    val uiState: StateFlow<ExploreUiState> = _uiState.asStateFlow()

    private val _inspiringTravelers = MutableStateFlow<List<InspiringProfileResponse>>(emptyList())
    val inspiringTravelers: StateFlow<List<InspiringProfileResponse>> = _inspiringTravelers.asStateFlow()

    private val _trendingTrips = MutableStateFlow<List<TrendingTrip>>(emptyList())
    val trendingTrips: StateFlow<List<TrendingTrip>> = _trendingTrips.asStateFlow()

    private val _trendingTags = MutableStateFlow<List<TopTag>>(emptyList())
    val trendingTags: StateFlow<List<TopTag>> = _trendingTags.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    fun refresh() { loadExploreScreen() }

    init { loadExploreScreen() }

    private fun setLoading() { _uiState.value = ExploreUiState.Loading }

    private fun loadExploreScreen() {
        viewModelScope.launch {
            setLoading()
            try {
                coroutineScope {
                    val tagsDeferred = async { getTrendingTagsUseCase().first() }
                    val tripsDeferred = async { getTrendingTripsUseCase().first() }
                    val travelersDeferred = async { getInspiringTravelersUseCase().first() }

                    val tagsResult = tagsDeferred.await()
                    val tripsResult = tripsDeferred.await()
                    val travelersResult = travelersDeferred.await()

                    if (tagsResult is BaseResult.Error ||
                        tripsResult is BaseResult.Error ||
                        travelersResult is BaseResult.Error
                    ) {
                        val errorMessage = listOfNotNull(
                            (tagsResult as? BaseResult.Error)?.error,
                            (tripsResult as? BaseResult.Error)?.error,
                            (travelersResult as? BaseResult.Error)?.error
                        ).joinToString(separator = ", ")

                        _uiState.value = ExploreUiState.Error(errorMessage)
                    } else {
                        _trendingTags.value = (tagsResult as BaseResult.Success).data
                        _trendingTrips.value = (tripsResult as BaseResult.Success).data
                        _inspiringTravelers.value = (travelersResult as BaseResult.Success).data
                        _uiState.value = ExploreUiState.Showing
                    }
                }
            } catch (e: Exception) {
                _uiState.value = ExploreUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
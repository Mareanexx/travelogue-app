package ru.mareanexx.feature_explore.presentation.explore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.mareanexx.feature_explore.domain.entity.InspiringProfile
import ru.mareanexx.feature_explore.domain.entity.TopTag
import ru.mareanexx.feature_explore.domain.entity.TrendingTrip
import ru.mareanexx.feature_explore.domain.usecase.GetInspiringTravelersUseCase
import ru.mareanexx.feature_explore.domain.usecase.GetTrendingTagsUseCase
import ru.mareanexx.feature_explore.domain.usecase.GetTrendingTripsUseCase
import ru.mareanexx.feature_explore.presentation.explore.viewmodel.event.ExploreEvent
import ru.mareanexx.feature_explore.presentation.explore.viewmodel.state.ExploreUiState
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getTrendingTagsUseCase: GetTrendingTagsUseCase,
    private val getTrendingTripsUseCase: GetTrendingTripsUseCase,
    private val getInspiringTravelersUseCase: GetInspiringTravelersUseCase,
    private val followUserUseCase: ru.mareanexx.network.domain.follows.usecase.FollowUserUseCase,
    private val unfollowUserUseCase: ru.mareanexx.network.domain.follows.usecase.UnfollowUserUseCase,
    private val reportUseCase: ru.mareanexx.network.domain.report.usecase.CreateReportUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<ExploreUiState>(ExploreUiState.Loading)
    val uiState: StateFlow<ExploreUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ExploreEvent>()
    val eventFlow: SharedFlow<ExploreEvent> = _eventFlow.asSharedFlow()

    private val _inspiringTravelers = MutableStateFlow<List<InspiringProfile>>(emptyList())
    val inspiringTravelers: StateFlow<List<InspiringProfile>> = _inspiringTravelers.asStateFlow()

    private val _trendingTrips = MutableStateFlow<List<TrendingTrip>>(emptyList())
    val trendingTrips: StateFlow<List<TrendingTrip>> = _trendingTrips.asStateFlow()

    private val _trendingTags = MutableStateFlow<List<TopTag>>(emptyList())
    val trendingTags: StateFlow<List<TopTag>> = _trendingTags.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    fun refresh() { loadExploreScreen() }

    init { loadExploreScreen() }

    private fun showToast(message: String?) {
        viewModelScope.launch {
            _eventFlow.emit(ExploreEvent.ShowToast(message ?: "Unknown error"))
        }
    }

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

    fun followUser(inspiringProfile: InspiringProfile) {
        viewModelScope.launch {
            followUserUseCase(inspiringProfile.id)
                .catch { exception -> showToast(exception.message) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { showToast(baseResult.error) }
                        is BaseResult.Success -> {
                            val changedProfile = inspiringProfile.copy(isFollowing = true)
                            _inspiringTravelers.value = _inspiringTravelers.value.map {
                                if (it.id == changedProfile.id) changedProfile else it
                            }
                        }
                    }
                }
        }
    }

    fun unfollowUser(inspiringProfile: InspiringProfile) {
        viewModelScope.launch {
            unfollowUserUseCase(inspiringProfile.id)
                .catch { exception -> showToast(exception.message) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { showToast(baseResult.error) }
                        is BaseResult.Success -> {
                            val changedProfile = inspiringProfile.copy(isFollowing = false)
                            _inspiringTravelers.value = _inspiringTravelers.value.map {
                                if (it.id == changedProfile.id) changedProfile else it
                            }
                        }
                    }
                }
        }
    }

    fun createReport(tripId: Int) {
        viewModelScope.launch {
            reportUseCase(tripId)
                .catch { exception -> showToast(exception.message) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { showToast(baseResult.error) }
                        is BaseResult.Success -> { showToast(baseResult.data) }
                    }
                }
        }
    }
}
package ru.mareanexx.travelogue.presentation.screens.explore.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.explore.entity.TrendingTrip
import ru.mareanexx.travelogue.domain.report.usecase.CreateReportUseCase
import ru.mareanexx.travelogue.domain.trip.usecase.GetTripByTagUseCase
import ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.event.ExploreEvent
import ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.state.TaggedTripsUiState
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTripByTagUseCase: GetTripByTagUseCase,
    private val reportUseCase: CreateReportUseCase
): ViewModel() {
    private val tagName = savedStateHandle["tagName"] ?: ""

    private val _uiState = MutableStateFlow<TaggedTripsUiState>(TaggedTripsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ExploreEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private fun setLoading() { _uiState.value = TaggedTripsUiState.Loading }
    private fun setErrorState() { _uiState.value = TaggedTripsUiState.Error }
    private fun setSuccessState(trips: List<TrendingTrip>) { _uiState.value = TaggedTripsUiState.Success(trips) }

    private fun showToast(message: String?) {
        viewModelScope.launch {
            _eventFlow.emit(ExploreEvent.ShowToast(message ?: "Unknown error"))
        }
    }

    fun retry() {
        viewModelScope.launch {
            _isRefreshing.value = true
            loadTrips()
            _isRefreshing.value = false
        }
    }

    init { loadTrips() }

    fun loadTrips() {
        viewModelScope.launch {
            getTripByTagUseCase(tagName)
                .onStart { setLoading() }
                .catch { exception -> showToast(exception.message) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { setErrorState() }
                        is BaseResult.Success -> {
                            setSuccessState(baseResult.data)
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
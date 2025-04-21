package ru.mareanexx.travelogue.presentation.screens.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.travelogue.data.trip.local.entity.TripEntity
import ru.mareanexx.travelogue.domain.trip.usecase.GetAuthorsTripsUseCase
import javax.inject.Inject

@HiltViewModel
class TripsViewModel @Inject constructor(
    private val getAuthorsTripsUseCase: GetAuthorsTripsUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Init)
    val uiState: StateFlow<ProfileUiState> get() = _uiState

    private val _tripsData = MutableStateFlow<List<TripEntity>>(emptyList())
    val tripsData: StateFlow<List<TripEntity>> get() = _tripsData

    init {
        loadTrips()
    }

    private fun setLoading() { _uiState.value = ProfileUiState.IsLoading }

    private fun setShowing() { _uiState.value = ProfileUiState.Showing }

    private fun showToast(message: String) { _uiState.value = ProfileUiState.ShowToast(message) }

    private fun loadTrips() {
        viewModelScope.launch {
            getAuthorsTripsUseCase()
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    showToast(exception.message.toString())
                }
                .collect { trips ->
                    _tripsData.value = trips
                    setShowing()
                }
        }
    }
}
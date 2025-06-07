package ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.mareanexx.feature_profiles.data.trip.mapper.toEditRequest
import ru.mareanexx.feature_profiles.data.trip.mapper.toForm
import ru.mareanexx.feature_profiles.data.trip.mapper.toRequest
import ru.mareanexx.feature_profiles.domain.trip.entity.Trip
import ru.mareanexx.feature_profiles.domain.trip.usecase.AddTripUseCase
import ru.mareanexx.feature_profiles.domain.trip.usecase.DeleteTripUseCase
import ru.mareanexx.feature_profiles.domain.trip.usecase.GetAuthorsTripsUseCase
import ru.mareanexx.feature_profiles.domain.trip.usecase.GetUpdatedTripStats
import ru.mareanexx.feature_profiles.domain.trip.usecase.UpdateTripUseCase
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.event.TripTypifiedDialog
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.event.TripsEvent
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.form.TripForm
import ru.mareanexx.feature_profiles.presentation.screens.profile.viewmodel.state.ProfileUiState
import ru.mareanexx.network.utils.data.BaseResult
import java.io.File
import java.nio.file.NoSuchFileException
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TripsViewModel @Inject constructor(
    private val getAuthorsTripsUseCase: GetAuthorsTripsUseCase,
    private val addTripUseCase: AddTripUseCase,
    private val deleteTripUseCase: DeleteTripUseCase,
    private val updateTripUseCase: UpdateTripUseCase,
    private val getUpdatedTripStats: GetUpdatedTripStats
): ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.IsLoading)
    val uiState: StateFlow<ProfileUiState> get() = _uiState.asStateFlow()

    private val _formState = MutableStateFlow(TripForm())
    val formState: StateFlow<TripForm> get() = _formState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<TripsEvent>()
    val eventFlow: SharedFlow<TripsEvent> = _eventFlow.asSharedFlow()

    private val _tripsData = MutableStateFlow<List<Trip>>(emptyList())
    val tripsData: StateFlow<List<Trip>> get() = _tripsData.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private fun setLoading() { _uiState.value = ProfileUiState.IsLoading }

    private fun setShowing() { _uiState.value = ProfileUiState.Showing }

    private fun showToast(message: String?) {
        viewModelScope.launch {
            _eventFlow.emit(TripsEvent.ShowToast(message ?: "Unknown error"))
        }
    }

    fun onShowTypifiedDialog(tripId: Int = -1, type: TripTypifiedDialog) {
        viewModelScope.launch {
            if (type == TripTypifiedDialog.CreateTag) {
                if (_formState.value.tagList.size == 3) _eventFlow.emit(TripsEvent.ShowToast("No more tags can be added"))
            }
            _eventFlow.emit(TripsEvent.ShowTypifiedDialog(tripId, type))
        }
    }

    fun loadTrips() {
        viewModelScope.launch {
            getAuthorsTripsUseCase()
                .onStart { setLoading() }
                .catch { exception -> showToast(exception.message) }
                .collect { trips ->
                    _tripsData.value = trips
                    setShowing()
                }
        }
    }

    fun refreshStatistics() {
        viewModelScope.launch {
            _isRefreshing.value = true
            getUpdatedTripStats()
                .onStart { setLoading() }
                .catch { exception ->
                    setShowing()
                    showToast(exception.message)
                }
                .collect { tripStats ->
                    val updated = _tripsData.value.map { trip ->
                        val stat = tripStats.find { it.id == trip.id }
                        if (stat != null) {
                            trip.copy(stepsNumber = stat.stepsNumber, daysNumber = stat.daysNumber)
                        } else trip
                    }
                    _tripsData.value = updated
                    _isRefreshing.value = false
                    setShowing()
                }
        }
    }

    private fun String.isNotEmptyOrBlank() = this.isNotBlank() && this.isNotEmpty()

    private fun onCheckButtonEnabled() {
        _formState.value = _formState.value.copy(
            buttonEnabled = _formState.value.name.isNotEmptyOrBlank()
                    && _formState.value.description.isNotEmptyOrBlank()
                    && (_formState.value.coverPhoto != null || _formState.value.coverPhotoPath != null)
        )
    }

    fun onDatesChanged(start: LocalDate, end: LocalDate?) { _formState.value = _formState.value.copy(startDate = start, endDate = end) }

    fun onConcreteTagDelete(requiredIndex: Int) {
        val newList = _formState.value.tagList.filterIndexed { index, _ -> index != requiredIndex }
        _formState.value = _formState.value.copy(tagList = newList)
    }

    fun addNewTag() {
        _formState.value = _formState.value.let {
            it.copy(tagList = it.tagList + it.newTagName, newTagName = "")
        }
    }

    fun onNewTagNameChanged(newValue: String) {
        _formState.value = _formState.value.copy(newTagName = newValue)
    }

    fun onTripNameChanged(value: String) {
        _formState.value = _formState.value.copy(name = value)
        onCheckButtonEnabled()
    }

    fun onDescriptionChanged(value: String) {
        _formState.value = _formState.value.copy(description = value)
        onCheckButtonEnabled()
    }

    fun onTripTimeStatusChanged(newStatus: ru.mareanexx.data.trip.type.TripTimeStatus) { _formState.value = _formState.value.copy(status = newStatus) }

    fun onTripVisibilityType(newType: ru.mareanexx.data.trip.type.TripVisibilityType) { _formState.value = _formState.value.copy(type = newType) }

    fun onCoverPhotoSelected(newCover: File) {
        _formState.value = _formState.value.copy(coverPhoto = newCover)
        onCheckButtonEnabled()
    }

    fun uploadNewTrip() {
        viewModelScope.launch {
            addTripUseCase(
                newTripRequest = _formState.value.toRequest(),
                coverPhoto = _formState.value.coverPhoto ?: throw NoSuchFileException("Cant upload file")
            )
                .onStart { setLoading() }
                .catch { exception -> showToast(exception.message) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { showToast(baseResult.error) }
                        is BaseResult.Success -> {
                            _tripsData.value = buildList {
                                add(baseResult.data)
                                addAll(_tripsData.value)
                            }
                            _uiState.value = ProfileUiState.Showing
                            _formState.value = TripForm()
                        }
                    }
                }
        }
    }

    fun deleteTrip(tripId: Int) {
        viewModelScope.launch {
            deleteTripUseCase(tripId)
                .onStart { setLoading() }
                .catch { exception ->
                    showToast(exception.message)
                }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> {
                            showToast(baseResult.error) }
                        is BaseResult.Success -> {
                            _tripsData.value = _tripsData.value.filter { it.id != tripId }
                        }
                    }
                }
        }
    }

    fun onEditPanelOpen(trip: Trip) {
        _formState.value = TripForm()
        _formState.value = trip.toForm()
        viewModelScope.launch { _eventFlow.emit(TripsEvent.ShowEditBottomSheet(true)) }
    }

    fun updateTrip() {
        viewModelScope.launch {
            updateTripUseCase(_formState.value.toEditRequest(), _formState.value.coverPhoto)
                .onStart { setLoading() }
                .catch { exception -> showToast(exception.message) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { showToast(baseResult.error) }
                        is BaseResult.Success -> {
                            val newList = _tripsData.value.map {
                                if (it.id == baseResult.data.id) baseResult.data else it
                            }
                            _tripsData.value = newList
                            _eventFlow.emit(TripsEvent.ShowEditBottomSheet(false))
                            setShowing()
                            _formState.value = TripForm()
                        }
                    }
                }
        }
    }
}
package ru.mareanexx.travelogue.presentation.screens.profile.viewmodel

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
import ru.mareanexx.travelogue.data.trip.local.type.TripTimeStatus
import ru.mareanexx.travelogue.data.trip.local.type.TripVisibilityType
import ru.mareanexx.travelogue.data.trip.mapper.toEditRequest
import ru.mareanexx.travelogue.data.trip.mapper.toForm
import ru.mareanexx.travelogue.data.trip.mapper.toRequest
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.trip.entity.Trip
import ru.mareanexx.travelogue.domain.trip.usecase.AddTripUseCase
import ru.mareanexx.travelogue.domain.trip.usecase.DeleteTripUseCase
import ru.mareanexx.travelogue.domain.trip.usecase.GetAuthorsTripsUseCase
import ru.mareanexx.travelogue.domain.trip.usecase.UpdateTripUseCase
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.event.ProfileEvent
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.form.TripForm
import ru.mareanexx.travelogue.presentation.screens.profile.viewmodel.state.ProfileUiState
import java.io.File
import java.nio.file.NoSuchFileException
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TripsViewModel @Inject constructor(
    private val getAuthorsTripsUseCase: GetAuthorsTripsUseCase,
    private val addTripUseCase: AddTripUseCase,
    private val deleteTripUseCase: DeleteTripUseCase,
    private val updateTripUseCase: UpdateTripUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Init)
    val uiState: StateFlow<ProfileUiState> get() = _uiState.asStateFlow()

    private val _formState = MutableStateFlow(TripForm())
    val formState: StateFlow<TripForm> get() = _formState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ProfileEvent>()
    val eventFlow: SharedFlow<ProfileEvent> = _eventFlow.asSharedFlow()

    private val _tripsData = MutableStateFlow<List<Trip>>(emptyList())
    val tripsData: StateFlow<List<Trip>> get() = _tripsData.asStateFlow()

    init { loadTrips() }

    private fun setLoading() { _uiState.value = ProfileUiState.IsLoading }

    private fun setShowing() { _uiState.value = ProfileUiState.Showing }

    private fun loadTrips() {
        viewModelScope.launch {
            getAuthorsTripsUseCase()
                .onStart { setLoading() }
                .catch { exception -> _eventFlow.emit(ProfileEvent.ShowToast(exception.message ?: "Unknown error")) }
                .collect { trips ->
                    _tripsData.value = trips
                    setShowing()
                }
        }
    }

    private fun String.isNotEmptyOrBlank() = this.isNotBlank() && this.isNotEmpty()

    private fun onCheckButtonEnabled() {
        _formState.value = _formState.value.copy(
            buttonEnabled = _formState.value.name.isNotEmptyOrBlank()
                    && _formState.value.description.isNotEmptyOrBlank()
                    && _formState.value.coverPhoto != null
        )
    }

    fun onDatesChanged(start: LocalDate, end: LocalDate?) { _formState.value = _formState.value.copy(startDate = start, endDate = end) }

    fun onConcreteTagDelete(requiredIndex: Int) {
        val newList = _formState.value.tagList.filterIndexed { index, _ -> index != requiredIndex }
        _formState.value = _formState.value.copy(tagList = newList)
    }

    fun addNewTag(tag: String = "") {
        val tempList = _formState.value.tagList
        if (tempList.size >= 3) return
        _formState.value = _formState.value.copy(tagList = tempList + tag)
    }

    fun onConcreteTagNameChanged(requiredIndex: Int, newVal: String) {
        val newList = _formState.value.tagList.mapIndexed { index, prevVal ->
            if (index == requiredIndex) newVal else prevVal
        }
        _formState.value = _formState.value.copy(tagList = newList)
    }

    fun onTripNameChanged(value: String) {
        _formState.value = _formState.value.copy(name = value)
        onCheckButtonEnabled()
    }

    fun onDescriptionChanged(value: String) {
        _formState.value = _formState.value.copy(description = value)
        onCheckButtonEnabled()
    }

    fun onTripTimeStatusChanged(newStatus: TripTimeStatus) { _formState.value = _formState.value.copy(status = newStatus) }

    fun onTripVisibilityType(newType: TripVisibilityType) { _formState.value = _formState.value.copy(type = newType) }

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
                .catch { exception ->
                    _eventFlow.emit(ProfileEvent.ShowToast(exception.message ?: "Unknown error"))
                }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> {
                            _eventFlow.emit(ProfileEvent.ShowToast(baseResult.error))
                        }
                        is BaseResult.Success -> {
                            _tripsData.value += baseResult.data
                            _uiState.value = ProfileUiState.Showing
                            _formState.value = TripForm()
                        }
                    }
                }
        }
    }

    fun onDeleteClicked(tripId: Int) {
        viewModelScope.launch { _eventFlow.emit(ProfileEvent.ShowDeleteDialog(tripId)) }
    }

    fun deleteTrip(tripId: Int) {
        viewModelScope.launch {
            deleteTripUseCase(tripId)
                .onStart { setLoading() }
                .catch { exception -> _eventFlow.emit(ProfileEvent.ShowToast(exception.message ?: "Unknown error")) }
        }
    }

    fun onEditPanelOpen(trip: Trip) {
        _formState.value = TripForm()
        _formState.value = trip.toForm()
        viewModelScope.launch { _eventFlow.emit(ProfileEvent.ShowEditBottomSheet) }
    }

    fun updateTrip() {
        viewModelScope.launch {
            updateTripUseCase(_formState.value.toEditRequest(), _formState.value.coverPhoto)
                .onStart { setLoading() }
                .catch { exception -> _eventFlow.emit(ProfileEvent.ShowToast(exception.message ?: "Unknown error")) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { _eventFlow.emit(ProfileEvent.ShowToast(baseResult.error)) }
                        is BaseResult.Success -> {
                            val newList = _tripsData.value.map {
                                if (it.id == baseResult.data.id) baseResult.data else it
                            }
                            _tripsData.value = newList
                            _eventFlow.emit(ProfileEvent.CloseEditBottomSheet)
                            _uiState.value = ProfileUiState.Showing
                            _formState.value = TripForm()
                        }
                    }
                }
        }
    }

}
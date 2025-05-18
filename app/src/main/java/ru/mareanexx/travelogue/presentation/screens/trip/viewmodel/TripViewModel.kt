package ru.mareanexx.travelogue.presentation.screens.trip.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mareanexx.travelogue.BuildConfig
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.data.mappoint.mapper.toEditRequest
import ru.mareanexx.travelogue.data.mappoint.mapper.toForm
import ru.mareanexx.travelogue.data.mappoint.mapper.toNewRequest
import ru.mareanexx.travelogue.data.mappoint.remote.dto.MapPointWithPhotos
import ru.mareanexx.travelogue.data.pointphoto.mapper.toDeleted
import ru.mareanexx.travelogue.data.trip.remote.dto.TripWithMapPoints
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.likes.usecase.AddLikeUseCase
import ru.mareanexx.travelogue.domain.likes.usecase.DeleteLikeUseCase
import ru.mareanexx.travelogue.domain.mappoint.entity.PointPhoto
import ru.mareanexx.travelogue.domain.mappoint.usecase.CreateMapPointUseCase
import ru.mareanexx.travelogue.domain.mappoint.usecase.DeleteMapPointUseCase
import ru.mareanexx.travelogue.domain.mappoint.usecase.EditMapPointUseCase
import ru.mareanexx.travelogue.domain.trip.usecase.GetTripWithMapPointsUseCase
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.event.BottomSheetType
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.event.DialogType
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.event.TripEvent
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.form.DateConstraints
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.form.MapPointForm
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.state.MapPointUiState
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.state.TripUiState
import ru.mareanexx.travelogue.utils.MarkerCreator
import java.io.File
import java.net.URL
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class TripViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTripWithMapPointsUseCase: GetTripWithMapPointsUseCase,
    private val createMapPointUseCase: CreateMapPointUseCase,
    private val updateMapPointUseCase: EditMapPointUseCase,
    private val deleteMapPointUseCase: DeleteMapPointUseCase,
    private val addLikeUseCase: AddLikeUseCase,
    private val deleteLikeUseCase: DeleteLikeUseCase
): ViewModel() {

    private val profileId: String = savedStateHandle["profileId"] ?: "me"
    private val tripId: Int = savedStateHandle["tripId"] ?: -1

    private val _tripData = MutableStateFlow<TripWithMapPoints?>(null)
    val tripData = _tripData.asStateFlow()

    private val _mapPointBitmaps = MutableStateFlow<Map<Int, Bitmap>>(emptyMap())
    val mapPointBitmaps = _mapPointBitmaps.asStateFlow()

    private val _focusedMapPointId = MutableStateFlow<Int?>(null)
    val focusedMapPointId = _focusedMapPointId.asStateFlow()

    private val _mapPointUiState = MutableStateFlow<MapPointUiState>(MapPointUiState.Init)
    val mapPointUiState = _mapPointUiState.asStateFlow()

    private val _mapPointForm = MutableStateFlow(MapPointForm())
    val mapPointForm = _mapPointForm.asStateFlow()

    private val _uiState = MutableStateFlow<TripUiState>(TripUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<TripEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init { getTripWithMapPoints() }

    fun setLoadingState() { _uiState.value = TripUiState.Loading }
    private fun setShowingState() { _uiState.value = TripUiState.Showing }
    private fun showToast(message: String?) {
        viewModelScope.launch { _eventFlow.emit(TripEvent.ShowToast(message ?: "Unknown error")) }
    }

    fun showTypifiedDialog(type: DialogType) {
        viewModelScope.launch {
            _eventFlow.emit(TripEvent.ShowTypifiedDialog(type))
        }
    }
    fun showBottomSheet(type: BottomSheetType) {
        viewModelScope.launch {
            _eventFlow.emit(TripEvent.ShowTypifiedBottomSheet(type))
        }
    }

    fun clearForm() { _mapPointForm.value = MapPointForm() }

    fun onMapPointFocused(id: Int) { _focusedMapPointId.value = id }

    fun retry() { getTripWithMapPoints() }

    private fun getTripWithMapPoints() {
        viewModelScope.launch {
            getTripWithMapPointsUseCase(profileId, tripId)
                .onStart { setLoadingState() }
                .catch { exception -> showTypifiedDialog(DialogType.Error) }
                .collect { result ->
                    when(result) {
                        is BaseResult.Error -> { showTypifiedDialog(DialogType.Error) }
                        is BaseResult.Success -> {
                            _tripData.value = result.data.copy(
                                mapPoints = result.data.mapPoints.sortedBy { it.mapPoint.arrivalDate }
                            )
                        }
                    }
                }
        }
    }

    private suspend fun changeEditedMapPointBitmap(data: MapPointWithPhotos) {
        val prevBitmap = _mapPointBitmaps.value[data.mapPoint.id]
        val imageUrl = data.photos.firstOrNull()?.filePath?.let { "${BuildConfig.API_FILES_URL}$it" }
        val bitmap = try {
            if (imageUrl != null) {
                withContext(Dispatchers.IO) {
                    val input = URL(imageUrl).openStream()
                    val loaded = BitmapFactory.decodeStream(input)
                    MarkerCreator.createMarkerBitmapFromBitmap(loaded)
                }
            } else prevBitmap
        } catch (_: Exception) { prevBitmap }

        val newBitmaps = _mapPointBitmaps.value.toMutableMap().apply { set(data.mapPoint.id, bitmap!!) }
        _mapPointBitmaps.value = newBitmaps
    }

    suspend fun preloadMapPointBitmaps(context: Context) {
        val result = _mapPointBitmaps.value.toMutableMap()
        val newPoints = _tripData.value?.mapPoints?.filter { it.mapPoint.id !in result }

        newPoints?.forEach { pointWithPhotos ->
            val imageUrl = pointWithPhotos.photos.firstOrNull()?.filePath?.let { "${BuildConfig.API_FILES_URL}$it" }

            val bitmap = try {
                if (imageUrl != null) {
                    withContext(Dispatchers.IO) {
                        val input = URL(imageUrl).openStream()
                        val loaded = BitmapFactory.decodeStream(input)
                        MarkerCreator.createMarkerBitmapFromBitmap(loaded)
                    }
                } else { MarkerCreator.createMarkerBitmap(context, R.drawable.cover_placeholder) }
            } catch (e: Exception) { MarkerCreator.createMarkerBitmap(context, R.drawable.cover_placeholder) }

            result[pointWithPhotos.mapPoint.id] = bitmap
        }
        _mapPointBitmaps.value = result
        setShowingState()
    }

    fun onMapPointNameChanged(newName: String) {
        _mapPointForm.value = _mapPointForm.value.copy(name = newName)
        checkButtonEnabled()
    }

    fun onMapPointDescriptionChanged(newDesc: String) { _mapPointForm.value = _mapPointForm.value.copy(description = newDesc) }

    fun onMapPointArrivalDateChanged(arrivalDateMillis: Long) {
        val arrivalDate = Instant.ofEpochMilli(arrivalDateMillis).atZone(ZoneId.systemDefault()).toLocalDate()
        _mapPointForm.value = _mapPointForm.value.copy(arrivalDate = arrivalDate)
    }

    fun setDateConstraints(newDateConstraints: DateConstraints) {
        _mapPointForm.value = _mapPointForm.value.copy(
            arrivalDate = newDateConstraints.lowerBound,
            dateConstraints = newDateConstraints
        )
    }

    fun onMapPointArrivalTimeChanged(hour: Int, minute: Int) {
        val localTime = LocalTime.of(hour, minute)
        val offset = ZoneOffset.systemDefault().rules.getOffset(Instant.now())
        val offsetTime = localTime.atOffset(offset)
        _mapPointForm.value = _mapPointForm.value.copy(arrivalTime = offsetTime)
    }

    fun onAddPhoto(photo: File) {
        val updated = _mapPointForm.value.photos + photo
        _mapPointForm.value = _mapPointForm.value.copy(photos = updated)
    }

    fun onRemovePhoto(photo: File) {
        val updated = _mapPointForm.value.photos - photo
        _mapPointForm.value = _mapPointForm.value.copy(photos = updated)
    }

    fun onRemovePhotoAndAddToDeletedList(pointPhoto: PointPhoto) {
        val updated = _mapPointForm.value.serverPhotos - pointPhoto
        val deletedPhotos = _mapPointForm.value.deletedPhotos
        _mapPointForm.value = _mapPointForm.value.copy(serverPhotos = updated, deletedPhotos = deletedPhotos + updated)
    }

    fun onUpdateStepCoordinates(lat: Double, lng: Double) {
        _mapPointForm.value = _mapPointForm.value.copy(latitude = lat, longitude = lng)
        checkButtonEnabled()
    }

    private fun checkButtonEnabled() {
        val form = _mapPointForm.value
        _mapPointForm.value = _mapPointForm.value.copy(
            buttonEnabled = form.longitude != Double.MIN_VALUE && form.latitude != Double.MIN_VALUE
                    && form.name.isNotEmpty()
        )
    }

    fun createMapPoint() {
        viewModelScope.launch {
            createMapPointUseCase(_mapPointForm.value.toNewRequest(tripId), photos = _mapPointForm.value.photos)
                .onStart { _mapPointUiState.value = MapPointUiState.Loading }
                .catch { exception -> showToast(exception.localizedMessage) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> {
                            showToast(baseResult.error)
                            _mapPointUiState.value = MapPointUiState.Error
                        }
                        is BaseResult.Success -> {
                            _tripData.value = _tripData.value?.copy(
                                mapPoints = (_tripData.value!!.mapPoints + baseResult.data).sortedBy { it.mapPoint.arrivalDate }
                            )
                            clearForm()
                            _focusedMapPointId.value = baseResult.data.mapPoint.id
                            _mapPointUiState.value = MapPointUiState.Success
                        }
                    }
                }
        }
    }

    fun onFillEditForm(newValue: MapPointWithPhotos) { _mapPointForm.value = newValue.toForm()  }

    fun editMapPoint() {
        viewModelScope.launch {
            updateMapPointUseCase(
                editMapPoint = _mapPointForm.value.toEditRequest(),
                deleted = _mapPointForm.value.deletedPhotos.map { it.toDeleted() },
                photos = _mapPointForm.value.photos
            )
                .onStart { _mapPointUiState.value = MapPointUiState.Loading }
                .catch { exception ->
                    showToast(exception.localizedMessage)
                    _mapPointUiState.value = MapPointUiState.Error
                }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> {
                            showToast(baseResult.error)
                            _mapPointUiState.value = MapPointUiState.Error
                        }
                        is BaseResult.Success -> {
                            changeEditedMapPointBitmap(baseResult.data)
                            _tripData.value = _tripData.value?.copy(
                                mapPoints = _tripData.value!!.mapPoints.map {
                                    if (it.mapPoint.id == baseResult.data.mapPoint.id) baseResult.data else it
                                }.sortedBy { it.mapPoint.arrivalDate }
                            )
                            clearForm()
                            _mapPointUiState.value = MapPointUiState.Success
                        }
                    }
                }
        }
    }

    fun deleteMapPoint(mapPointId: Int) {
        viewModelScope.launch {
            deleteMapPointUseCase(mapPointId)
                .onStart { _mapPointUiState.value = MapPointUiState.Loading }
                .catch { exception ->
                    showToast(exception.localizedMessage)
                    _mapPointUiState.value = MapPointUiState.Error
                }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> {
                            showToast(baseResult.error)
                            _mapPointUiState.value = MapPointUiState.Error
                        }
                        is BaseResult.Success -> {
                            _tripData.value = _tripData.value?.copy(
                                mapPoints = _tripData.value!!.mapPoints.filter { it.mapPoint.id != mapPointId }
                            )
                            _mapPointBitmaps.value = _mapPointBitmaps.value.filterKeys { it != mapPointId }
                            clearForm()
                            _mapPointUiState.value = MapPointUiState.Success
                        }
                    }
                }
        }
    }

    fun likeMapPoint(mapPointId: Int) {
        viewModelScope.launch {
            addLikeUseCase(mapPointId)
                .catch { exception -> showToast(exception.localizedMessage) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { showToast(baseResult.error) }
                        is BaseResult.Success -> {
                            _tripData.value = _tripData.value?.copy(
                                mapPoints = _tripData.value!!.mapPoints.map {
                                    if (it.mapPoint.id == mapPointId) it.copy(mapPoint = it.mapPoint.copy(
                                        isLiked = true,
                                        likesNumber = it.mapPoint.likesNumber + 1
                                    )) else it
                                }
                            )
                            _mapPointUiState.value = MapPointUiState.Success
                        }
                    }
                }
        }
    }

    fun removeLike(mapPointId: Int) {
        viewModelScope.launch {
            deleteLikeUseCase(mapPointId)
                .catch { exception -> showToast(exception.localizedMessage) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { showToast(baseResult.error) }
                        is BaseResult.Success -> {
                            _tripData.value = _tripData.value?.copy(
                                mapPoints = _tripData.value!!.mapPoints.map {
                                    if (it.mapPoint.id == mapPointId) it.copy(mapPoint = it.mapPoint.copy(
                                        isLiked = false,
                                        likesNumber = it.mapPoint.likesNumber - 1
                                    )) else it
                                }
                            )
                            _mapPointUiState.value = MapPointUiState.Success
                        }
                    }
                }
        }
    }
}
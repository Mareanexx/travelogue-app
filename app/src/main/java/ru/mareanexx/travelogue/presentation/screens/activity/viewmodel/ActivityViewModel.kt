package ru.mareanexx.travelogue.presentation.screens.activity.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import ru.mareanexx.travelogue.data.mappoint.remote.dto.TrendingTripWithPoints
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.report.usecase.CreateReportUseCase
import ru.mareanexx.travelogue.domain.trip.usecase.GetActivityUseCase
import ru.mareanexx.travelogue.presentation.screens.activity.viewmodel.event.ActivityEvent
import ru.mareanexx.travelogue.presentation.screens.activity.viewmodel.state.ActivityUiState
import ru.mareanexx.travelogue.utils.MarkerCreator
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val getActivityUseCase: GetActivityUseCase,
    private val reportUseCase: CreateReportUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ActivityUiState>(ActivityUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ActivityEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _mapPointBitmaps = MutableStateFlow<Map<Int, Bitmap>>(emptyMap())
    val mapPointBitmaps = _mapPointBitmaps.asStateFlow()

    init { loadActivity() }

    private fun setLoadingState() { _uiState.value = ActivityUiState.Loading }
    private fun setErrorState() { _uiState.value = ActivityUiState.Error }
    private fun setNoFollowsState() { _uiState.value = ActivityUiState.NoFollows }

    private fun showToast(message: String?) {
        viewModelScope.launch {
            _eventFlow.emit(ActivityEvent.ShowToast(message ?: "Unknown error"))
        }
    }

    fun preloadAllMapPointBitmaps(context: Context, trips: List<TrendingTripWithPoints>) {
        viewModelScope.launch {
            val result = _mapPointBitmaps.value.toMutableMap()

            trips.forEach { trip ->
                trip.mapPoints.forEach foreach@ { point ->
                    if (result.containsKey(point.id)) return@foreach

                    val bitmap = try {
                        val url = point.previewPhotoPath
                        if (url.isNotBlank()) {
                            withContext(Dispatchers.IO) {
                                val input = URL("${BuildConfig.API_FILES_URL}$url").openStream()
                                val loaded = BitmapFactory.decodeStream(input)
                                MarkerCreator.createMarkerBitmapFromBitmap(loaded)
                            }
                        } else {
                            MarkerCreator.createMarkerBitmap(context, R.drawable.cover_placeholder)
                        }
                    } catch (e: Exception) {
                        MarkerCreator.createMarkerBitmap(context, R.drawable.cover_placeholder)
                    }

                    result[point.id] = bitmap
                }
            }

            _mapPointBitmaps.value = result
        }
    }


    fun loadActivity() {
        viewModelScope.launch {
            getActivityUseCase()
                .onStart { setLoadingState() }
                .catch { setErrorState() }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> {
                            if (baseResult.code == 204) setNoFollowsState()
                            else setErrorState()
                        }
                        is BaseResult.Success -> {
                            _uiState.value = ActivityUiState.Success(baseResult.data)
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
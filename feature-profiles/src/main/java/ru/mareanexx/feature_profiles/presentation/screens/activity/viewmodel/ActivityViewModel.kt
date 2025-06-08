package ru.mareanexx.feature_profiles.presentation.screens.activity.viewmodel

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
import ru.mareanexx.common.utils.ApiConfig
import ru.mareanexx.common.utils.MarkerCreator
import ru.mareanexx.core.common.R
import ru.mareanexx.feature_profiles.data.mappoint.remote.dto.ActivityTripWithMapPoints
import ru.mareanexx.feature_profiles.domain.activity.usecase.GetActivityUseCase
import ru.mareanexx.feature_profiles.presentation.screens.activity.viewmodel.event.ActivityEvent
import ru.mareanexx.feature_profiles.presentation.screens.activity.viewmodel.state.ActivityUiState
import ru.mareanexx.network.domain.report.usecase.CreateReportUseCase
import ru.mareanexx.network.utils.data.BaseResult
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val getActivityUseCase: GetActivityUseCase,
    private val reportUseCase: CreateReportUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ActivityUiState>(ActivityUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

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

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            loadActivity()
            _isRefreshing.value = false
        }
    }

    fun preloadAllMapPointBitmaps(context: Context, trips: List<ActivityTripWithMapPoints>) {
        viewModelScope.launch {
            val result = _mapPointBitmaps.value.toMutableMap()

            trips.forEach { trip ->
                trip.mapPoints.forEach foreach@ { point ->
                    if (result.containsKey(point.id)) return@foreach

                    val bitmap = try {
                        val url = point.previewPhotoPath
                        if (url.isNotBlank()) {
                            withContext(Dispatchers.IO) {
                                val input = URL("${ApiConfig.apiFilesUrl}$url").openStream()
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
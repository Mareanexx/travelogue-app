package ru.mareanexx.travelogue.presentation.screens.explore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import ru.mareanexx.travelogue.domain.common.BaseResult
import ru.mareanexx.travelogue.domain.explore.entity.SearchProfile
import ru.mareanexx.travelogue.domain.explore.entity.SearchResult
import ru.mareanexx.travelogue.domain.explore.usecase.SearchUseCase
import ru.mareanexx.travelogue.domain.follows.usecase.FollowUserUseCase
import ru.mareanexx.travelogue.domain.follows.usecase.UnfollowUserUseCase
import ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.event.ExploreEvent
import ru.mareanexx.travelogue.presentation.screens.explore.viewmodel.state.SearchUiState
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val followUserUseCase: FollowUserUseCase,
    private val unfollowUserUseCase: UnfollowUserUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Init)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ExploreEvent>()
    val eventFlow: SharedFlow<ExploreEvent> = _eventFlow.asSharedFlow()

    private val searchQuery = MutableStateFlow("")

    private val _searchResults = MutableStateFlow(SearchResult(emptyList(), emptyList()))
    val searchResults: StateFlow<SearchResult> = _searchResults.asStateFlow()

    private fun setLoadingState() { _uiState.value = SearchUiState.Loading }
    private fun setInitState() { _uiState.value = SearchUiState.Init }
    private fun setErrorState() { _uiState.value = SearchUiState.Error }
    private fun setSuccessState() { _uiState.value = SearchUiState.Success }
    private fun showToast(message: String?) {
        viewModelScope.launch {
            _eventFlow.emit(ExploreEvent.ShowToast(message ?: "Unknown exception"))
        }
    }

    fun onQueryChanged(query: String) {
        searchQuery.value = query
        if (query.isBlank()) { setInitState() }
        else { setLoadingState() }
    }

    init {
        viewModelScope.launch {
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .collectLatest { query ->
                    try {
                        searchUseCase(query).collect { baseResult ->
                            when (baseResult) {
                                is BaseResult.Success -> {
                                    _searchResults.value = baseResult.data
                                    setSuccessState()
                                }
                                is BaseResult.Error -> {
                                    Log.d("SEARCH_ERROR", "query = '$query' ${baseResult.error}")
                                    setErrorState()
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.d("SEARCH_EXCEPTION", "query = '$query'  ${e.message ?: "what"}")
                        e.printStackTrace()
                        setErrorState()
                    }
                }
        }
    }

    fun clearQuery() {
        searchQuery.value = ""
        setInitState()
    }

    fun followUser(profile: SearchProfile) {
        viewModelScope.launch {
            followUserUseCase(profile.id)
                .catch { exception -> showToast(exception.message) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { showToast(baseResult.error) }
                        is BaseResult.Success -> {
                            val changedProfile = profile.copy(isFollowing = true)
                            _searchResults.value = _searchResults.value.copy(
                                profiles = _searchResults.value.profiles.map {
                                    if (it.id == changedProfile.id) changedProfile else it
                                }
                            )
                        }
                    }
                }
        }
    }

    fun unfollowUser(profile: SearchProfile) {
        viewModelScope.launch {
            unfollowUserUseCase(profile.id)
                .catch { exception -> showToast(exception.message) }
                .collect { baseResult ->
                    when(baseResult) {
                        is BaseResult.Error -> { showToast(baseResult.error) }
                        is BaseResult.Success -> {
                            val changedProfile = profile.copy(isFollowing = false)
                            _searchResults.value = _searchResults.value.copy(
                                profiles = _searchResults.value.profiles.map {
                                    if (it.id == changedProfile.id) changedProfile else it
                                }
                            )
                        }
                    }
                }
        }
    }
}

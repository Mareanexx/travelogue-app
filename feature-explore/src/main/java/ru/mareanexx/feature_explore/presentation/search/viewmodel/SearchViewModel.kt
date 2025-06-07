package ru.mareanexx.feature_explore.presentation.search.viewmodel

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
import ru.mareanexx.feature_explore.domain.entity.SearchProfile
import ru.mareanexx.feature_explore.domain.entity.SearchResult
import ru.mareanexx.feature_explore.domain.usecase.SearchUseCase
import ru.mareanexx.feature_explore.presentation.screens.SearchOverlayState
import ru.mareanexx.feature_explore.presentation.explore.viewmodel.event.ExploreEvent
import ru.mareanexx.feature_explore.presentation.search.state.SearchUiState
import ru.mareanexx.network.utils.data.BaseResult
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val followUserUseCase: ru.mareanexx.network.domain.follows.usecase.FollowUserUseCase,
    private val unfollowUserUseCase: ru.mareanexx.network.domain.follows.usecase.UnfollowUserUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Init)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ExploreEvent>()
    val eventFlow: SharedFlow<ExploreEvent> = _eventFlow.asSharedFlow()

    private val _searchOverlayState = MutableStateFlow(SearchOverlayState())
    val searchOverlayState get() = _searchOverlayState.asStateFlow()

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
        _searchOverlayState.value = _searchOverlayState.value.copy(query = query)
        if (query.isBlank()) { setInitState() }
        else { setLoadingState() }
    }

    init {
        viewModelScope.launch {
            _searchOverlayState
                .debounce(500)
                .distinctUntilChanged()
                .filter { it.query.isNotBlank() }
                .collectLatest { overlayState ->
                    try {
                        searchUseCase(overlayState.query).collect { baseResult ->
                            when (baseResult) {
                                is BaseResult.Success -> {
                                    _searchResults.value = baseResult.data
                                    setSuccessState()
                                }
                                is BaseResult.Error -> { setErrorState() }
                            }
                        }
                    } catch (e: Exception) { setErrorState() }
                }
        }
    }

    fun clearQuery() {
        _searchOverlayState.value = _searchOverlayState.value.copy(query = "")
        _searchResults.value = SearchResult(emptyList(), emptyList())
        setInitState()
    }

    fun onToggleSearchOverlay(isOpen: Boolean) { _searchOverlayState.value = _searchOverlayState.value.copy(isActive = isOpen) }

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

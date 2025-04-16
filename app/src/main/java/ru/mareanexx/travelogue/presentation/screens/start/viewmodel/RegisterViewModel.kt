package ru.mareanexx.travelogue.presentation.screens.start.viewmodel



sealed class RegisterUiState {
    object Init : RegisterUiState()
    object IsLoading : RegisterUiState()
    data class Success(val message: String) : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}

//class RegisterViewModel @Inject constructor(
//    private val registerUseCase: RegisterUseCase
//) {
//    private val _registerState = MutableStateFlow<RegisterUiState>(RegisterUiState.Init)
//    val registerState: StateFlow<RegisterUiState> get() = _registerState
//
//
//
//
//}
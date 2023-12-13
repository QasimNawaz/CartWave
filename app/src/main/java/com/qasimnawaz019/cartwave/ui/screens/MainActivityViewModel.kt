package com.qasimnawaz019.cartwave.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.cartwave.ui.screens.graphs.AuthScreenInfo
import com.qasimnawaz019.cartwave.ui.screens.graphs.MainScreenInfo
import com.qasimnawaz019.cartwave.ui.screens.graphs.OnBoardingScreen
import com.qasimnawaz019.data.repository.datastore.DataStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val repository: DataStoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        getDestination()
    }

    private fun getDestination() {
        viewModelScope.launch {
            if (repository.readOnBoardingState().first()) {
                if (repository.readAccessToken().first()
                        .isNullOrBlank() || !repository.readRememberState().first()
                ) {
                    _uiState.emit(
                        UiState(
                            startDestination = AuthScreenInfo.Login.route, error = null
                        )
                    )
                } else {
                    _uiState.emit(
                        UiState(
                            startDestination = MainScreenInfo.Main.route, error = null
                        )
                    )
                }
            } else {
                _uiState.emit(
                    UiState(
                        startDestination = OnBoardingScreen.Welcome.route, error = null
                    )
                )
            }
        }
    }
}

data class UiState(val startDestination: String? = null, val error: String? = null)
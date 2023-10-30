package com.qasimnawaz019.cartwave.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.cartwave.ui.screens.graphs.AuthScreenInfo
import com.qasimnawaz019.cartwave.ui.screens.graphs.Graph.ERROR_DIALOG
import com.qasimnawaz019.cartwave.ui.screens.graphs.MainScreenInfo
import com.qasimnawaz019.cartwave.ui.screens.graphs.OnBoardingScreen
import com.qasimnawaz019.data.repository.datastore.DataStoreRepository
import com.qasimnawaz019.domain.usecase.CategoriesUseCase
import com.qasimnawaz019.domain.utils.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val categoriesUseCase: CategoriesUseCase, private val repository: DataStoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            categoriesUseCase.execute(Unit).collect {
                when (it) {
                    is ApiResponse.Error.GenericError -> _uiState.emit(
                        UiState(
                            startDestination = ERROR_DIALOG,
                            error = it.message
                        )
                    )

                    is ApiResponse.Error.HttpError -> _uiState.emit(
                        UiState(
                            startDestination = ERROR_DIALOG,
                            error = it.message
                        )
                    )

                    is ApiResponse.Error.NoNetwork -> _uiState.emit(
                        UiState(
                            startDestination = ERROR_DIALOG,
                            error = it.cause.message
                        )
                    )

                    is ApiResponse.Error.SerializationError -> _uiState.emit(
                        UiState(
                            startDestination = ERROR_DIALOG,
                            error = it.message
                        )
                    )

                    is ApiResponse.Success -> {
                        repository.saveCategories(it.body.toSet())
                        getDestination()
                    }
                }
            }
        }
    }

    private fun getDestination() {
        viewModelScope.launch {
            if (repository.readOnBoardingState().first()) {
                if (repository.readAccessToken().first().isNullOrBlank()) {
                    _uiState.emit(
                        UiState(
                            startDestination = AuthScreenInfo.Login.route,
                            error = null
                        )
                    )
                } else {
                    _uiState.emit(
                        UiState(
                            startDestination = MainScreenInfo.Main.route,
                            error = null
                        )
                    )
                }
            } else {
                _uiState.emit(
                    UiState(
                        startDestination = OnBoardingScreen.Welcome.route,
                        error = null
                    )
                )
            }
        }
    }
}

data class UiState(val startDestination: String? = null, val error: String? = null)
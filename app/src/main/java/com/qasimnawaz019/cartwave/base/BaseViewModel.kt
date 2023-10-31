package com.qasimnawaz019.cartwave.base

import androidx.lifecycle.ViewModel
import com.qasimnawaz019.domain.utils.ApiResponse
import com.qasimnawaz019.domain.utils.NetworkUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel<T> : ViewModel() {

    internal val _networkUiState = MutableStateFlow<NetworkUiState<T>>(NetworkUiState.Empty)
    val networkUiState: StateFlow<NetworkUiState<T>> = _networkUiState.asStateFlow()

    suspend fun Flow<ApiResponse<T>>.asUiState() {
        this.collect {
            when (it) {
                is ApiResponse.Error.NoNetwork -> {
                    _networkUiState.emit(
                        NetworkUiState.Error(
                            code = 12029,
                            error = "${it.cause.message}"
                        )
                    )
                }

                is ApiResponse.Error.HttpError -> {
                    _networkUiState.emit(
                        NetworkUiState.Error(
                            code = it.code,
                            error = "${it.message}"
                        )
                    )
                }

                is ApiResponse.Error.SerializationError -> {
                    _networkUiState.emit(NetworkUiState.Error(error = "${it.message}"))
                }

                is ApiResponse.Error.GenericError -> {
                    _networkUiState.emit(NetworkUiState.Error(error = "${it.message}"))
                }

                is ApiResponse.Success -> {
                    _networkUiState.emit(NetworkUiState.Success(it.body))
                }
            }
        }
    }
}
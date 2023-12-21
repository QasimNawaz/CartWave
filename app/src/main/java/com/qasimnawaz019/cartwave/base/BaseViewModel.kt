package com.qasimnawaz019.cartwave.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.utils.NetworkCall
import com.qasimnawaz019.domain.utils.NetworkUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel<T> : ViewModel() {

//    internal val _loadingUiState = MutableStateFlow<Boolean>(false)
//    val loadingUiState: StateFlow<Boolean> = _loadingUiState.asStateFlow()
//
//    internal val _errorUiState = MutableStateFlow<String?>(null)
//    val errorUiState: StateFlow<String?> = _errorUiState.asStateFlow()

    internal val _networkUiState = MutableStateFlow<NetworkUiState<T>>(NetworkUiState.Empty)
    val networkUiState: StateFlow<NetworkUiState<T>> = _networkUiState.asStateFlow()


    suspend fun Flow<NetworkCall<BaseResponse<T>>>.asUiState() {
        this.collect {
            when (it) {
                is NetworkCall.Error.NoNetwork -> {
                    Log.d("asUiState", "ApiResponse.Error.NoNetwork Emit")
                    _networkUiState.emit(
                        NetworkUiState.Error(
                            code = 12029,
                            error = "${it.cause.message}"
                        )
                    )
                }

                is NetworkCall.Error.HttpError -> {
                    Log.d("asUiState", "ApiResponse.Error.HttpError Emit")
                    _networkUiState.emit(
                        NetworkUiState.Error(
                            code = it.code,
                            error = "${it.message}"
                        )
                    )
                }

                is NetworkCall.Error.SerializationError -> {
                    Log.d("asUiState", "ApiResponse.Error.SerializationError Emit")
                    _networkUiState.emit(NetworkUiState.Error(error = "${it.message}"))
                }

                is NetworkCall.Error.GenericError -> {
                    Log.d("asUiState", "ApiResponse.Error.GenericError Emit")
                    _networkUiState.emit(NetworkUiState.Error(error = "${it.message}"))
                }

                is NetworkCall.Success -> {
                    Log.d("asUiState", "ApiResponse.Success Emit")
                    if (it.data.success && it.data.data != null) {
                        _networkUiState.emit(NetworkUiState.Success(it.data.data!!))
                    } else {
                        _networkUiState.emit(NetworkUiState.Error(error = it.data.message))
                    }
                }
            }
        }
    }
}
package com.qasimnawaz019.cartwave.ui.screens.checkout

import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.cartwave.base.BaseViewModel
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.usecase.cart.GetUserCartUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import com.qasimnawaz019.domain.utils.NetworkUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckOutScreenViewModel(
    private val getUserCartUseCase: GetUserCartUseCase,
) : BaseViewModel<List<Product>>() {

    private val _totalAmount = MutableStateFlow<Int>(0)
    val totalAmount: StateFlow<Int> = _totalAmount.asStateFlow()

    init {
        getUserCart()
    }

    private fun getUserCart() {
        viewModelScope.launch {
            getUserCartUseCase.execute(Unit).collect { networkCall ->
                when (networkCall) {
                    is NetworkCall.Error.NoNetwork -> {
                        _networkUiState.emit(
                            NetworkUiState.Error(
                                code = 12029,
                                error = "${networkCall.cause.message}"
                            )
                        )
                    }

                    is NetworkCall.Error.HttpError -> {
                        _networkUiState.emit(
                            NetworkUiState.Error(
                                code = networkCall.code,
                                error = "${networkCall.message}"
                            )
                        )
                    }

                    is NetworkCall.Error.SerializationError -> {
                        _networkUiState.emit(NetworkUiState.Error(error = "${networkCall.message}"))
                    }

                    is NetworkCall.Error.GenericError -> {
                        _networkUiState.emit(NetworkUiState.Error(error = "${networkCall.message}"))
                    }

                    is NetworkCall.Success -> {
                        if (networkCall.data.success && networkCall.data.data != null) {
                            networkCall.data.data?.let { carts ->
                                _totalAmount.emit(carts.sumOf {
                                    (it.sellingPrice?.replace(",", "")?.toIntOrNull()
                                        ?: 0) * it.cartQty
                                } + 9)
                                _networkUiState.emit(NetworkUiState.Success(carts))
                            }
                        } else {
                            _networkUiState.emit(NetworkUiState.Error(error = networkCall.data.message))
                        }
                    }
                }
            }
        }
    }
}
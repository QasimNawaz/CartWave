package com.qasimnawaz019.cartwave.ui.screens.cart

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.cartwave.base.BaseViewModel
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.usecase.AddToCartUseCase
import com.qasimnawaz019.domain.usecase.GetUserCartUseCase
import com.qasimnawaz019.domain.usecase.RemoveFromCartUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import com.qasimnawaz019.domain.utils.NetworkUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartScreenViewModel(
    private val getUserCartUseCase: GetUserCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase
) : BaseViewModel<List<Product>>() {

    private val _subTotal = MutableStateFlow<Int>(0)
    val subTotal: StateFlow<Int> = _subTotal.asStateFlow()

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
                                _subTotal.emit(carts.sumOf {
                                    (it.sellingPrice?.replace(",", "")?.toIntOrNull()
                                        ?: 0) * it.cartQty
                                })
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

    fun addToCart(productId: Int, cartQty: Int, updatedData: SnapshotStateList<Product>) {
        viewModelScope.launch {
            addToCartUseCase.execute(AddToCartUseCase.Params(productId, cartQty))
                .collectLatest { networkCall ->
                    when (networkCall) {
                        is NetworkCall.Success -> {
                            _networkUiState.value.let { uiState ->
                                when (uiState) {
                                    is NetworkUiState.Success -> {
                                        _subTotal.emit(updatedData.sumOf {
                                            (it.sellingPrice?.replace(",", "")?.toIntOrNull()
                                                ?: 0) * it.cartQty
                                        })
                                        _networkUiState.emit(
                                            NetworkUiState.Success(
                                                updatedData
                                            )
                                        )
                                    }

                                    else -> {}
                                }
                            }
                        }

                        else -> {}
                    }
                }
        }
    }

    fun removeFromCart(productId: Int, updatedData: SnapshotStateList<Product>) {
        viewModelScope.launch {
            removeFromCartUseCase.execute(RemoveFromCartUseCase.Params(productId))
                .collectLatest { networkCall ->
                    when (networkCall) {
                        is NetworkCall.Success -> {
                            _networkUiState.value.let { uiState ->
                                when (uiState) {
                                    is NetworkUiState.Success -> {
                                        _subTotal.emit(updatedData.sumOf {
                                            (it.sellingPrice?.replace(",", "")?.toIntOrNull()
                                                ?: 0) * it.cartQty
                                        })
                                        _networkUiState.emit(
                                            NetworkUiState.Success(
                                                updatedData
                                            )
                                        )
                                    }

                                    else -> {}
                                }
                            }
                        }

                        else -> {}
                    }
                }
        }
    }
}
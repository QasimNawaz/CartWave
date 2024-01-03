package com.qasimnawaz019.cartwave.ui.screens.checkout

import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.cartwave.base.BaseViewModel
import com.qasimnawaz019.domain.dto.order.PlaceOrderRequestDto
import com.qasimnawaz019.domain.model.Address
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.usecase.address.GetPrimaryAddressUseCase
import com.qasimnawaz019.domain.usecase.cart.GetUserCartUseCase
import com.qasimnawaz019.domain.usecase.order.PlaceOrderUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import com.qasimnawaz019.domain.utils.NetworkUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckOutScreenViewModel(
    private val getUserCartUseCase: GetUserCartUseCase,
    private val getPrimaryAddressUseCase: GetPrimaryAddressUseCase,
    private val placeOrderUseCase: PlaceOrderUseCase
) : BaseViewModel<Address>() {

    private val _totalAmount = MutableStateFlow<Int>(0)
    val totalAmount: StateFlow<Int> = _totalAmount.asStateFlow()

    private val _userCartUiState =
        MutableStateFlow<NetworkUiState<List<Product>>>(NetworkUiState.Empty)
    val userCartUiState: StateFlow<NetworkUiState<List<Product>>> = _userCartUiState.asStateFlow()

    private val _placeUiState = MutableStateFlow<NetworkUiState<String>>(NetworkUiState.Empty)
    val placeUiState: StateFlow<NetworkUiState<String>> = _placeUiState.asStateFlow()

    init {
        getUserCart()
        getPrimaryAddress()
    }

    fun getPrimaryAddress() {
        viewModelScope.launch {
            _networkUiState.emit(NetworkUiState.Loading)
            getPrimaryAddressUseCase.execute(Unit).asUiState()
        }
    }

    private fun getUserCart() {
        viewModelScope.launch {
            _userCartUiState.emit(NetworkUiState.Loading)
            getUserCartUseCase.execute(Unit).collect { networkCall ->
                when (networkCall) {
                    is NetworkCall.Error.NoNetwork -> {
                        _userCartUiState.emit(
                            NetworkUiState.Error(
                                code = 12029, error = "${networkCall.cause.message}"
                            )
                        )
                    }

                    is NetworkCall.Error.HttpError -> {
                        _userCartUiState.emit(
                            NetworkUiState.Error(
                                code = networkCall.code, error = "${networkCall.message}"
                            )
                        )
                    }

                    is NetworkCall.Error.SerializationError -> {
                        _userCartUiState.emit(NetworkUiState.Error(error = "${networkCall.message}"))
                    }

                    is NetworkCall.Error.GenericError -> {
                        _userCartUiState.emit(NetworkUiState.Error(error = "${networkCall.message}"))
                    }

                    is NetworkCall.Success -> {
                        if (networkCall.data.success && networkCall.data.data != null) {
                            networkCall.data.data?.let { carts ->
                                _totalAmount.emit(carts.sumOf {
                                    (it.sellingPrice?.replace(",", "")?.toIntOrNull()
                                        ?: 0) * it.cartQty
                                } + 9)
                                _userCartUiState.emit(NetworkUiState.Success(carts))
                            }
                        } else {
                            _userCartUiState.emit(NetworkUiState.Error(error = networkCall.data.message))
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun placeOrder(dto: PlaceOrderRequestDto) {
        viewModelScope.launch {
            _placeUiState.emit(NetworkUiState.Loading)
            placeOrderUseCase.execute(PlaceOrderUseCase.Params(dto)).asUiState(_placeUiState)
        }
    }
}

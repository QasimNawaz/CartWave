package com.qasimnawaz019.cartwave.ui.screens.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.cartwave.base.BaseViewModel
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.usecase.cart.AddToCartUseCase
import com.qasimnawaz019.domain.usecase.cart.RemoveFromCartUseCase
import com.qasimnawaz019.domain.usecase.favourite.AddToFavouriteUseCase
import com.qasimnawaz019.domain.usecase.favourite.RemoveFromFavouriteUseCase
import com.qasimnawaz019.domain.usecase.product.ProductDetailUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import com.qasimnawaz019.domain.utils.NetworkUiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productId: Int,
    private val productDetailUseCase: ProductDetailUseCase,
    private val addToFavouriteUseCase: AddToFavouriteUseCase,
    private val removeFromFavouriteUseCase: RemoveFromFavouriteUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase
) : BaseViewModel<Product>() {

    init {
        getProductDetail(productId)
    }

    private fun getProductDetail(productId: Int) {
        viewModelScope.launch {
            _networkUiState.emit(NetworkUiState.Loading)
            productDetailUseCase.execute(ProductDetailUseCase.Params(productId)).asUiState()
        }
    }

    fun addToFavourite(productId: Int) {
        viewModelScope.launch {
            addToFavouriteUseCase.execute(AddToFavouriteUseCase.Params(productId)).collectLatest {
                when (it) {
                    is NetworkCall.Success -> {
                        Log.d("HomeScreen", "addToFavourite Success")
                        _networkUiState.value.let { uiState ->
                            when (uiState) {
                                is NetworkUiState.Success -> {
                                    Log.d(
                                        "HomeScreen",
                                        "addToFavourite _networkUiState collectLatest Success"
                                    )
                                    _networkUiState.emit(
                                        NetworkUiState.Success(
                                            uiState.data.copy(
                                                isFavourite = true
                                            )
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

    fun removeFromFavourite(productId: Int) {
        viewModelScope.launch {
            removeFromFavouriteUseCase.execute(RemoveFromFavouriteUseCase.Params(productId))
                .collectLatest {
                    when (it) {
                        is NetworkCall.Success -> {
                            Log.d("HomeScreen", "removeFromFavourite Success")
                            _networkUiState.value.let { uiState ->
                                when (uiState) {
                                    is NetworkUiState.Success -> {
                                        Log.d(
                                            "HomeScreen",
                                            "removeFromFavourite _networkUiState collectLatest Success"
                                        )
                                        _networkUiState.emit(
                                            NetworkUiState.Success(
                                                uiState.data.copy(
                                                    isFavourite = false
                                                )
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

    fun addToCart(productId: Int, cartQty: Int) {
        viewModelScope.launch {
            addToCartUseCase.execute(AddToCartUseCase.Params(productId, cartQty))
                .collectLatest {
                    when (it) {
                        is NetworkCall.Success -> {
                            Log.d("HomeScreen", "removeFromFavourite Success")
                            _networkUiState.value.let { uiState ->
                                when (uiState) {
                                    is NetworkUiState.Success -> {
                                        Log.d(
                                            "HomeScreen",
                                            "removeFromFavourite _networkUiState collectLatest Success"
                                        )
                                        _networkUiState.emit(
                                            NetworkUiState.Success(
                                                uiState.data.copy(
                                                    cartQty = cartQty
                                                )
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

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            removeFromCartUseCase.execute(RemoveFromCartUseCase.Params(productId))
                .collectLatest {
                    when (it) {
                        is NetworkCall.Success -> {
                            Log.d("HomeScreen", "removeFromFavourite Success")
                            _networkUiState.value.let { uiState ->
                                when (uiState) {
                                    is NetworkUiState.Success -> {
                                        Log.d(
                                            "HomeScreen",
                                            "removeFromFavourite _networkUiState collectLatest Success"
                                        )
                                        _networkUiState.emit(
                                            NetworkUiState.Success(
                                                uiState.data.copy(
                                                    cartQty = 0
                                                )
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
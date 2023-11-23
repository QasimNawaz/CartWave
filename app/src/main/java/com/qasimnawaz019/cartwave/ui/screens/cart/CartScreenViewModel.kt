package com.qasimnawaz019.cartwave.ui.screens.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.usecase.AddToCartDatabaseUseCase
import com.qasimnawaz019.domain.usecase.GetCartDatabaseUseCase
import com.qasimnawaz019.domain.usecase.RemoveFavouriteDatabaseUseCase
import com.qasimnawaz019.domain.usecase.RemoveFromCartDatabaseUseCase
import com.qasimnawaz019.domain.usecase.UpdateCartDatabaseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartScreenViewModel(
    private val getCartDatabaseUseCase: GetCartDatabaseUseCase,
    private val updateCartDatabaseUseCase: UpdateCartDatabaseUseCase,
    private val removeFromCartDatabaseUseCase: RemoveFromCartDatabaseUseCase
) : ViewModel() {

    private val _cartProducts = MutableStateFlow<List<Product>>(emptyList())
    val cartProducts: StateFlow<List<Product>> = _cartProducts.asStateFlow()

    private val _subTotal = MutableStateFlow<Double>(0.0)
    val subTotal: StateFlow<Double> = _subTotal.asStateFlow()

    init {
        getMyCart()
    }

    private fun getMyCart() {
        viewModelScope.launch {
            getCartDatabaseUseCase.execute(Unit).collect { list ->
                Log.d("CartScreenViewModel", "getMyCart: $list")
                _subTotal.emit(list.sumOf { it.price })
                _cartProducts.emit(list)
            }
        }
    }

    fun updateCartQty(product: Product) {
        viewModelScope.launch {
            Log.d("CartScreenViewModel", "updateCartQty: $product")
            if (product.cartQty >= 1) {
                updateCartDatabaseUseCase.execute(UpdateCartDatabaseUseCase.Params(product))
            } else {
                product.id?.let {
                    removeFromCartDatabaseUseCase.execute(
                        RemoveFromCartDatabaseUseCase.Params(it)
                    )
                }
            }
        }
    }

    fun removeFromCart(id: Int) {
        viewModelScope.launch {
            removeFromCartDatabaseUseCase.execute(RemoveFromCartDatabaseUseCase.Params(id))
        }
    }
}
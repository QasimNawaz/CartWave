package com.qasimnawaz019.cartwave.ui.screens.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckOutScreenViewModel(
) : ViewModel() {

    private val _cartProducts = MutableStateFlow<List<Product>>(emptyList())
    val cartProducts: StateFlow<List<Product>> = _cartProducts.asStateFlow()

    private val _totalAmount = MutableStateFlow<Double>(0.0)
    val totalAmount: StateFlow<Double> = _totalAmount.asStateFlow()

    init {
        getMyCart()
    }

    private fun getMyCart() {
        viewModelScope.launch {
//            getCartDatabaseUseCase.execute(Unit).collect { list ->
//                Log.d("CheckOutScreenViewModel", "getMyCart: $list")
//                _totalAmount.emit(list.sumOf { it.price * it.cartQty } + 9)
//                _cartProducts.emit(list)
//            }
        }
    }
}
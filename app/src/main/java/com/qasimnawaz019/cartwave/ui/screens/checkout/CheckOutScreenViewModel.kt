package com.qasimnawaz019.cartwave.ui.screens.checkout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.usecase.GetCartDatabaseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckOutScreenViewModel(
    private val getCartDatabaseUseCase: GetCartDatabaseUseCase,
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
            getCartDatabaseUseCase.execute(Unit).collect { list ->
                Log.d("CheckOutScreenViewModel", "getMyCart: $list")
                _totalAmount.emit(list.sumOf { it.price } + 9)
                _cartProducts.emit(list)
            }
        }
    }
}
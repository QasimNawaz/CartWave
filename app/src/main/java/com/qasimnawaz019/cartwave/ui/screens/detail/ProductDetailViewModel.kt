package com.qasimnawaz019.cartwave.ui.screens.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.cartwave.base.BaseViewModel
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.usecase.AddToCartDatabaseUseCase
import com.qasimnawaz019.domain.usecase.AddToFavouriteDatabaseUseCase
import com.qasimnawaz019.domain.usecase.ProductUseCase
import com.qasimnawaz019.domain.usecase.RemoveFavouriteDatabaseUseCase
import com.qasimnawaz019.domain.usecase.RemoveFromCartDatabaseUseCase
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productUseCase: ProductUseCase,
    private val addToCartDatabaseUseCase: AddToCartDatabaseUseCase,
    private val removeFromCartDatabaseUseCase: RemoveFromCartDatabaseUseCase,
    private val addToFavouriteDatabaseUseCase: AddToFavouriteDatabaseUseCase,
    private val removeFavouriteDatabaseUseCase: RemoveFavouriteDatabaseUseCase
) : BaseViewModel<Product>() {

    fun getProductDetail(productId: Int) {
//        viewModelScope.launch {
//            _networkUiState.emit(NetworkUiState.Loading)
//            productUseCase.execute(ProductUseCase.Params(productId)).asUiState()
//        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            addToCartDatabaseUseCase.execute(AddToCartDatabaseUseCase.Params(product))
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            removeFromCartDatabaseUseCase.execute(RemoveFromCartDatabaseUseCase.Params(productId))
        }
    }

    fun addToFavourite(product: Product) {
        viewModelScope.launch {
            Log.d("ProductDetailScr", "addToFavourite: $product")
            addToFavouriteDatabaseUseCase.execute(AddToFavouriteDatabaseUseCase.Params(product))
        }
    }

    fun removeFavourite(id: Int) {
        viewModelScope.launch {
            removeFavouriteDatabaseUseCase.execute(RemoveFavouriteDatabaseUseCase.Params(id))
        }
    }
}
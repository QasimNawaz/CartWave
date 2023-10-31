package com.qasimnawaz019.cartwave.ui.screens.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.cartwave.base.BaseViewModel
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.usecase.AddToFavouriteDatabaseUseCase
import com.qasimnawaz019.domain.usecase.ProductsUseCase
import com.qasimnawaz019.domain.usecase.RemoveFavouriteDatabaseUseCase
import com.qasimnawaz019.domain.utils.NetworkUiState
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val productsUseCase: ProductsUseCase,
    private val addToFavouriteDatabaseUseCase: AddToFavouriteDatabaseUseCase,
    private val removeFavouriteDatabaseUseCase: RemoveFavouriteDatabaseUseCase
) : BaseViewModel<List<Product>>() {

//    init {
//        getProducts(10)
//    }

    fun getProducts(limit: Int) {
        viewModelScope.launch {
            Log.d("HomeScren", "getProducts: $limit")
            _networkUiState.emit(NetworkUiState.Loading)
            productsUseCase.execute(ProductsUseCase.Params(limit)).asUiState()
        }
    }

    fun addToFavourite(product: Product) {
        viewModelScope.launch {
            addToFavouriteDatabaseUseCase.execute(AddToFavouriteDatabaseUseCase.Params(product))
        }
    }

    fun removeFavourite(id: Int) {
        viewModelScope.launch {
            removeFavouriteDatabaseUseCase.execute(RemoveFavouriteDatabaseUseCase.Params(id))
        }
    }

    fun updateFavouriteStatus(product: Product, values: List<Product>) {
        viewModelScope.launch {
            _networkUiState.emit(NetworkUiState.Success(values))
            if (product.isFavourite) {
                product.id?.let {
                    removeFavouriteDatabaseUseCase.execute(
                        RemoveFavouriteDatabaseUseCase.Params(it)
                    )
                    _networkUiState
                }
            } else {
                addToFavouriteDatabaseUseCase.execute(AddToFavouriteDatabaseUseCase.Params(product))
            }
        }
    }
}
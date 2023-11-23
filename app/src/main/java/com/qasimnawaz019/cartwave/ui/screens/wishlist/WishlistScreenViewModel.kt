package com.qasimnawaz019.cartwave.ui.screens.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.usecase.GetFavouritesDatabaseUseCase
import com.qasimnawaz019.domain.usecase.RemoveFavouriteDatabaseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WishlistScreenViewModel(
    private val getFavouritesDatabaseUseCase: GetFavouritesDatabaseUseCase,
    private val removeFavouriteDatabaseUseCase: RemoveFavouriteDatabaseUseCase
) : ViewModel() {

    private val _favouriteProducts = MutableStateFlow<List<Product>>(emptyList())
    val favouriteProducts: StateFlow<List<Product>> = _favouriteProducts.asStateFlow()

    init {
        getWishlist()
    }

    private fun getWishlist() {
        viewModelScope.launch {
            getFavouritesDatabaseUseCase.execute(Unit).collect {
                _favouriteProducts.emit(it)
            }
        }
    }

    fun removeFavourite(id: Int, products: List<Product>) {
        viewModelScope.launch {
            _favouriteProducts.emit(products)
            removeFavouriteDatabaseUseCase.execute(RemoveFavouriteDatabaseUseCase.Params(id))
        }
    }
}
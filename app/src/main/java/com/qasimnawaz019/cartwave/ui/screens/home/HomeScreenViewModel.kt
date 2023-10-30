package com.qasimnawaz019.cartwave.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.usecase.AddToFavouriteDatabaseUseCase
import com.qasimnawaz019.domain.usecase.ProductsUseCase
import com.qasimnawaz019.domain.usecase.RemoveFavouriteDatabaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val productsUseCase: ProductsUseCase,
    private val addToFavouriteDatabaseUseCase: AddToFavouriteDatabaseUseCase,
    private val removeFavouriteDatabaseUseCase: RemoveFavouriteDatabaseUseCase
) : ViewModel() {

    fun getProducts(): Flow<PagingData<Product>> =
        productsUseCase.execute(Unit).cachedIn(viewModelScope)

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
}
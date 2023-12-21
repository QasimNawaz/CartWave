package com.qasimnawaz019.cartwave.ui.screens.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.qasimnawaz019.cartwave.base.BaseViewModel
import com.qasimnawaz019.domain.model.ProductsByCategoryItem
import com.qasimnawaz019.domain.usecase.AddToFavouriteUseCase
import com.qasimnawaz019.domain.usecase.ProductsByCategoryUseCase
import com.qasimnawaz019.domain.usecase.ProductsGroupBySubCategoryUseCase
import com.qasimnawaz019.domain.usecase.RemoveFromFavouriteUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import com.qasimnawaz019.domain.utils.NetworkUiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val title: String,
    private val addToFavouriteUseCase: AddToFavouriteUseCase,
    private val removeFromFavouriteUseCase: RemoveFromFavouriteUseCase,
    private val productsGroupBySubCategoryUseCase: ProductsGroupBySubCategoryUseCase,
    private val productsByCategoryUseCase: ProductsByCategoryUseCase
) : BaseViewModel<List<ProductsByCategoryItem>>() {

//    init {
//        getProductsByCategory()
//    }
//
//    private fun getProductsByCategory() {
//        viewModelScope.launch {
//            Log.d("HomeScreenViewModel", "getProductsByCategory")
//            _networkUiState.emit(NetworkUiState.Loading)
//            productsByCategoryUseCase.execute(Unit).asUiState()
//        }
//    }

    init {
        getProductsGroupBySubCategory(title)
    }

    fun getProductsGroupBySubCategory(category: String, refresh: Boolean = false) {
        viewModelScope.launch {
            Log.d("HomeScreenViewModel", "getProductsGroupBySubCategory: $category")
            if (!refresh) {
                _networkUiState.emit(NetworkUiState.Loading)
            }
            productsGroupBySubCategoryUseCase.execute(
                ProductsGroupBySubCategoryUseCase.Params(
                    category
                )
            ).asUiState()
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
                                    uiState.data as List<ProductsByCategoryItem>
                                    val s = uiState.data.map { innerList ->
                                        ProductsByCategoryItem(
                                            category = innerList.category,
                                            products = innerList.products?.map { productX ->
                                                if (productX.id == productId) {
                                                    productX.copy(isFavourite = true)
                                                } else {
                                                    productX
                                                }
                                            }
                                        )
                                    }
                                    _networkUiState.emit(NetworkUiState.Success(s))
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
                                        uiState.data as List<ProductsByCategoryItem>
                                        val s = uiState.data.map { innerList ->
                                            ProductsByCategoryItem(
                                                category = innerList.category,
                                                products = innerList.products?.map { productX ->
                                                    if (productX.id == productId) {
                                                        productX.copy(isFavourite = false)
                                                    } else {
                                                        productX
                                                    }
                                                }
                                            )
                                        }
                                        _networkUiState.emit(NetworkUiState.Success(s))
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

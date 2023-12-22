package com.qasimnawaz019.cartwave.ui.screens.wishlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.usecase.favourite.FavouritesPagingUseCase
import com.qasimnawaz019.domain.usecase.favourite.RemoveFromFavouriteUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WishlistScreenViewModel(
    private val removeFromFavouriteUseCase: RemoveFromFavouriteUseCase,
    private val favouritesPagingUseCase: FavouritesPagingUseCase
) : ViewModel() {

    private val _reLoad = MutableStateFlow<Boolean>(false)
    val reLoad: StateFlow<Boolean> = _reLoad.asStateFlow()

    val pagingData: Flow<PagingData<Product>> =
        favouritesPagingUseCase.execute(Unit).cachedIn(viewModelScope)


    fun removeFromFavourite(productId: Int) {
        viewModelScope.launch {
            removeFromFavouriteUseCase.execute(RemoveFromFavouriteUseCase.Params(productId))
                .collectLatest {
                    when (it) {
                        is NetworkCall.Success -> {
                            if (it.data.success) {
                                Log.d("WishlistScreen", "removeFromFavourite Success")
                                _reLoad.value = true
                            }
                        }

                        else -> {}
                    }
                }
        }
    }

    fun updateReload(reLoad: Boolean = false) {
        _reLoad.value = reLoad
    }
}
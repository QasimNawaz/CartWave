package com.qasimnawaz019.domain.repository

import androidx.paging.PagingData
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.flow.Flow

interface FavouriteProductsRepo {

    suspend fun addToFavourite(productId: Int): Flow<NetworkCall<BaseResponse<String>>>

    suspend fun removeFromFavourite(productId: Int): Flow<NetworkCall<BaseResponse<String>>>

    fun getFavouritesPaging(): Flow<PagingData<Product>>

}
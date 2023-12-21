package com.qasimnawaz019.domain.repository

import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.flow.Flow

interface UserCartRepo {

    suspend fun addToCart(
        productId: Int, cartQty: Int
    ): Flow<NetworkCall<BaseResponse<String>>>

    suspend fun removeFromCart(productId: Int): Flow<NetworkCall<BaseResponse<String>>>

    suspend fun getUserCart(): Flow<NetworkCall<BaseResponse<List<Product>>>>
}
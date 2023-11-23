package com.qasimnawaz019.data.repository.dataSource

import com.qasimnawaz019.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getFavouriteProducts(): Flow<List<Product>>

    suspend fun addToFavouriteProduct(product: Product)

    suspend fun removeFavouriteById(id: Int)

    suspend fun getMyCarts(): Flow<List<Product>>

    suspend fun addToCart(product: Product)

    suspend fun updateCart(product: Product)

    suspend fun removeCartById(id: Int)

}
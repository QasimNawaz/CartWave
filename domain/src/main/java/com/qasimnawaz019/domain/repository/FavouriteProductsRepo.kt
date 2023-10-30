package com.qasimnawaz019.domain.repository

import com.qasimnawaz019.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface FavouriteProductsRepo {
    suspend fun getFavouriteProducts(): Flow<List<Product>>

    suspend fun addToFavouriteProduct(product: Product)

    suspend fun removeFavouriteById(id: Int)
}
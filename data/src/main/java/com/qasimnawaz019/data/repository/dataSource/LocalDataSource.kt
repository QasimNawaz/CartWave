package com.qasimnawaz019.data.repository.dataSource

import com.qasimnawaz019.domain.model.Product

interface LocalDataSource {
    suspend fun getFavouriteProducts(): List<Product>

    suspend fun addToFavouriteProduct(product: Product)

    suspend fun removeFavouriteById(id: Int)

    suspend fun getMyCarts(): List<Product>

    suspend fun addToCart(product: Product)

    suspend fun removeCartById(id: Int)

}
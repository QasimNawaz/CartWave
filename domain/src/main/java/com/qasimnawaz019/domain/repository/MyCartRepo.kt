package com.qasimnawaz019.domain.repository

import com.qasimnawaz019.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface MyCartRepo {

    suspend fun getMyCarts(): Flow<List<Product>>

    suspend fun addToCart(product: Product)

    suspend fun updateCart(product: Product)

    suspend fun removeCartById(id: Int)
}
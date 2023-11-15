package com.qasimnawaz019.domain.repository

import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ProductsRepo {
    suspend fun getProducts(limit: Int): Flow<ApiResponse<List<Product>>>

    suspend fun getProductDetail(productId: Int): Flow<ApiResponse<Product>>

}
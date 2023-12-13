package com.qasimnawaz019.domain.repository

import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.model.ProductsByCategoryItem
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.flow.Flow

interface ProductsRepo {
    suspend fun getProducts(limit: Int): Flow<NetworkCall<List<Product>>>

    suspend fun getProductDetail(productId: Int): Flow<NetworkCall<Product>>

    suspend fun getProductByCategory(): Flow<NetworkCall<List<ProductsByCategoryItem>>>

}
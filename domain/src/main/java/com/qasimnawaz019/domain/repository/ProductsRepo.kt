package com.qasimnawaz019.domain.repository

import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.model.ProductsByCategoryItem
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.flow.Flow

interface ProductsRepo {
    suspend fun getProductDetail(productId: Int): Flow<NetworkCall<BaseResponse<Product>>>

    suspend fun getProductByCategory(): Flow<NetworkCall<BaseResponse<List<ProductsByCategoryItem>>>>

    suspend fun getProductsGroupBySubCategory(category: String): Flow<NetworkCall<BaseResponse<List<ProductsByCategoryItem>>>>

}
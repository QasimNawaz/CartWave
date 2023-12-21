package com.qasimnawaz019.data.repository.repoImpl

import android.util.Log
import com.qasimnawaz019.data.repository.dataSource.RemoteDataSource
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.model.ProductsByCategoryItem
import com.qasimnawaz019.domain.repository.ProductsRepo
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductsRepoImpl(
    private val remoteData: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : ProductsRepo {

    override suspend fun getProductDetail(productId: Int): Flow<NetworkCall<BaseResponse<Product>>> {
        return flow {
            emit(remoteData.getProductDetail(productId))
        }.flowOn(ioDispatcher)
    }

    override suspend fun getProductByCategory(): Flow<NetworkCall<BaseResponse<List<ProductsByCategoryItem>>>> {
        Log.d("ProductsRepoImpl", "getProductsByCategory")
        return flow {
            emit(remoteData.getProductsByCategory())
        }.flowOn(ioDispatcher)
    }

    override suspend fun getProductsGroupBySubCategory(category: String): Flow<NetworkCall<BaseResponse<List<ProductsByCategoryItem>>>> {
        val response = remoteData.getProductsGroupBySubCategory(category)
        return flow {
            emit(response)
        }.flowOn(ioDispatcher)
    }

}
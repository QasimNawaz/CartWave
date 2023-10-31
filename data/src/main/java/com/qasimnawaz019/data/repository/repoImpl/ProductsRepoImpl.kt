package com.qasimnawaz019.data.repository.repoImpl

import com.qasimnawaz019.data.database.dao.FavouriteProductsDao
import com.qasimnawaz019.data.repository.dataSource.RemoteDataSource
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.ProductsRepo
import com.qasimnawaz019.domain.utils.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductsRepoImpl(
    private val remoteData: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
    private val productsDao: FavouriteProductsDao
) : ProductsRepo {

    override suspend fun getProducts(limit: Int): Flow<ApiResponse<List<Product>>> {
        return flow {
            val remoteProducts = remoteData.getProducts(limit)
            val cacheProducts = productsDao.getFavouriteEntities().associateBy { it.id }
            when (remoteProducts) {
                is ApiResponse.Success -> {
                    remoteProducts.body.onEach { product ->
                        product.isFavourite = cacheProducts.containsKey(product.id)
                    }
                    emit(remoteProducts)
                }

                else -> {
                    emit(remoteProducts)
                }
            }
//            emit(remoteProducts)
        }.flowOn(ioDispatcher)
    }

}
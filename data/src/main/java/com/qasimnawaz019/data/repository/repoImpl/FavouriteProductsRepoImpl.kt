package com.qasimnawaz019.data.repository.repoImpl

import com.qasimnawaz019.data.repository.dataSource.LocalDataSource
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.FavouriteProductsRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FavouriteProductsRepoImpl(
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : FavouriteProductsRepo {
    override suspend fun getFavouriteProducts(): Flow<List<Product>> {
        return flow {
            emit(localDataSource.getFavouriteProducts())
        }.flowOn(ioDispatcher)
    }

    override suspend fun addToFavouriteProduct(product: Product) {
        localDataSource.addToFavouriteProduct(product)
    }

    override suspend fun removeFavouriteById(id: Int) {
        localDataSource.removeFavouriteById(id)
    }

}
package com.qasimnawaz019.data.repository.repoImpl

import com.qasimnawaz019.data.repository.dataSource.LocalDataSource
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.MyCartRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MyCartRepoImpl(
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : MyCartRepo {
    override suspend fun getMyCarts(): Flow<List<Product>> {
        return flow {
            emit(localDataSource.getMyCarts())
        }.flowOn(ioDispatcher)
    }

    override suspend fun addToCart(product: Product) {
        localDataSource.addToCart(product)
    }

    override suspend fun removeCartById(id: Int) {
        localDataSource.removeCartById(id)
    }
}
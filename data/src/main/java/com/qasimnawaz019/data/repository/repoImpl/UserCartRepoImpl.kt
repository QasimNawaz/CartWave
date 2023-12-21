package com.qasimnawaz019.data.repository.repoImpl

import com.qasimnawaz019.data.repository.dataSource.RemoteDataSource
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.UserCartRepo
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserCartRepoImpl(
    private val remoteData: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : UserCartRepo {
    override suspend fun addToCart(
        productId: Int,
        cartQty: Int
    ): Flow<NetworkCall<BaseResponse<String>>> {
        return flow {
            emit(remoteData.addToCart(productId, cartQty))
        }.flowOn(ioDispatcher)
    }

    override suspend fun removeFromCart(productId: Int): Flow<NetworkCall<BaseResponse<String>>> {
        return flow {
            emit(remoteData.removeFromCart(productId))
        }.flowOn(ioDispatcher)
    }

    override suspend fun getUserCart(): Flow<NetworkCall<BaseResponse<List<Product>>>> {
        return flow {
            emit(remoteData.getUserCart())
        }.flowOn(ioDispatcher)
    }
}
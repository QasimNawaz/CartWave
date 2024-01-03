package com.qasimnawaz019.data.repository.repoImpl

import com.qasimnawaz019.data.repository.dataSource.RemoteDataSource
import com.qasimnawaz019.domain.dto.order.PlaceOrderRequestDto
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.repository.OrderRepo
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class OrderRepoImpl(
    private val remoteData: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : OrderRepo {
    override suspend fun placeOrder(placeOrderRequestDto: PlaceOrderRequestDto): Flow<NetworkCall<BaseResponse<String>>> {
        return flow {
            emit(remoteData.placeOrder(placeOrderRequestDto))
        }.flowOn(ioDispatcher)
    }
}
package com.qasimnawaz019.domain.usecase.order

import com.qasimnawaz019.domain.dto.order.PlaceOrderRequestDto
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.repository.OrderRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class PlaceOrderUseCase(
    private val orderRepo: OrderRepo,
    private val ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<PlaceOrderUseCase.Params, @JvmSuppressWildcards Flow<NetworkCall<BaseResponse<String>>>> {

    data class Params(val placeOrderRequestDto: PlaceOrderRequestDto)

    override suspend fun execute(params: Params): Flow<NetworkCall<BaseResponse<String>>> {
        return orderRepo.placeOrder(params.placeOrderRequestDto).flowOn(ioDispatcher)
    }
}
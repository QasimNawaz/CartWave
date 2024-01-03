package com.qasimnawaz019.domain.repository

import com.qasimnawaz019.domain.dto.order.PlaceOrderRequestDto
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.flow.Flow

interface OrderRepo {
    suspend fun placeOrder(placeOrderRequestDto: PlaceOrderRequestDto): Flow<NetworkCall<BaseResponse<String>>>
}
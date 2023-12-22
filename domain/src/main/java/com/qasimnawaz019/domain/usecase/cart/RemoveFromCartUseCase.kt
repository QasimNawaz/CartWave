package com.qasimnawaz019.domain.usecase.cart

import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.repository.UserCartRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class RemoveFromCartUseCase(
    private val userCartRepo: UserCartRepo,
    private val ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<RemoveFromCartUseCase.Params, @JvmSuppressWildcards Flow<NetworkCall<BaseResponse<String>>>> {
    data class Params(val productId: Int)

    override suspend fun execute(params: Params): Flow<NetworkCall<BaseResponse<String>>> {
        return userCartRepo.removeFromCart(params.productId).flowOn(ioDispatcher)
    }


}
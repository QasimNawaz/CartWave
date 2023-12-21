package com.qasimnawaz019.domain.usecase

import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.UserCartRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetUserCartUseCase(
    private val userCartRepo: UserCartRepo,
    private val ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<Unit, @JvmSuppressWildcards Flow<NetworkCall<BaseResponse<List<Product>>>>> {


    override suspend fun execute(params: Unit): Flow<NetworkCall<BaseResponse<List<Product>>>> {
        return userCartRepo.getUserCart().flowOn(ioDispatcher)
    }


}
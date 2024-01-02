package com.qasimnawaz019.domain.usecase.address

import com.qasimnawaz019.domain.model.Address
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.repository.AddressRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetPrimaryAddressUseCase(
    private val addressRepo: AddressRepo,
    private val ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<Unit, @JvmSuppressWildcards Flow<NetworkCall<BaseResponse<Address>>>> {
    override suspend fun execute(params: Unit): @JvmSuppressWildcards Flow<NetworkCall<BaseResponse<Address>>> {
        return addressRepo.getPrimaryAddress().flowOn(ioDispatcher)
    }
}
package com.qasimnawaz019.domain.usecase.address

import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.repository.AddressRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class UpdatePrimaryAddressUseCase(
    private val addressRepo: AddressRepo,
    private val ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<UpdatePrimaryAddressUseCase.Params, @JvmSuppressWildcards Flow<NetworkCall<BaseResponse<String>>>> {
    data class Params(val addressId: Int)

    override suspend fun execute(params: Params): @JvmSuppressWildcards Flow<NetworkCall<BaseResponse<String>>> {
        return addressRepo.updatePrimaryAddress(params.addressId).flowOn(ioDispatcher)
    }
}
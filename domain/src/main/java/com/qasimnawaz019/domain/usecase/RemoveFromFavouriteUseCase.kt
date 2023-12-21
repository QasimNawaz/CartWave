package com.qasimnawaz019.domain.usecase

import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.repository.FavouriteProductsRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class RemoveFromFavouriteUseCase(
    private val favouriteProductsRepo: FavouriteProductsRepo,
    private val ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<RemoveFromFavouriteUseCase.Params, @JvmSuppressWildcards Flow<NetworkCall<BaseResponse<String>>>> {
    data class Params(val productId: Int)

    override suspend fun execute(params: Params): Flow<NetworkCall<BaseResponse<String>>> {
        return favouriteProductsRepo.removeFromFavourite(params.productId).flowOn(ioDispatcher)
    }


}
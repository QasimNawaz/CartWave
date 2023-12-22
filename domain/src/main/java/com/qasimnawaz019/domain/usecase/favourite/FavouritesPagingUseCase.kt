package com.qasimnawaz019.domain.usecase.favourite

import androidx.paging.PagingData
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.FavouriteProductsRepo
import com.qasimnawaz019.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


class FavouritesPagingUseCase(
    private val favouriteProductsRepo: FavouriteProductsRepo,
    private val ioDispatcher: CoroutineDispatcher,
) : UseCase<Unit, @JvmSuppressWildcards Flow<PagingData<Product>>> {
    override fun execute(params: Unit): Flow<PagingData<Product>> {
        return favouriteProductsRepo.getFavouritesPaging().flowOn(ioDispatcher)
    }


}
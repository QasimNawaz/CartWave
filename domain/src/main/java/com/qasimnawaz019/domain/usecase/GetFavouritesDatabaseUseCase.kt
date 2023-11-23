package com.qasimnawaz019.domain.usecase

import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.FavouriteProductsRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetFavouritesDatabaseUseCase(
    private val favouriteProductsRepo: FavouriteProductsRepo,
    private val ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<Unit, @JvmSuppressWildcards Flow<List<Product>>> {
    override suspend fun execute(params: Unit): Flow<List<Product>> {
        return favouriteProductsRepo.getFavouriteProducts().flowOn(ioDispatcher)
    }

}
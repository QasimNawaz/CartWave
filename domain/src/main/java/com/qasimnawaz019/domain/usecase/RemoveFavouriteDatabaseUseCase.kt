package com.qasimnawaz019.domain.usecase

import com.qasimnawaz019.domain.repository.FavouriteProductsRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase

class RemoveFavouriteDatabaseUseCase(
    private val favouriteProductsRepo: FavouriteProductsRepo,
) : SuspendUseCase<RemoveFavouriteDatabaseUseCase.Params, Unit> {

    data class Params(val id: Int)

    override suspend fun execute(params: Params) =
        favouriteProductsRepo.removeFavouriteById(params.id)

}
package com.qasimnawaz019.domain.usecase

import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.FavouriteProductsRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase

class AddToFavouriteDatabaseUseCase(
    private val favouriteProductsRepo: FavouriteProductsRepo,
) : SuspendUseCase<AddToFavouriteDatabaseUseCase.Params, Unit> {

    data class Params(val product: Product)

    override suspend fun execute(params: Params) =
        favouriteProductsRepo.addToFavouriteProduct(params.product)

}
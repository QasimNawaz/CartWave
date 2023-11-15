package com.qasimnawaz019.domain.usecase

import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.MyCartRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase

class AddToCartDatabaseUseCase(
    private val myCartRepo: MyCartRepo,
) : SuspendUseCase<AddToCartDatabaseUseCase.Params, Unit> {

    data class Params(val product: Product)

    override suspend fun execute(params: Params) =
        myCartRepo.addToCart(params.product)

}
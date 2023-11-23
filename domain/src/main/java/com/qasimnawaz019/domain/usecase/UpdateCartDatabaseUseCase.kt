package com.qasimnawaz019.domain.usecase

import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.MyCartRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase
class UpdateCartDatabaseUseCase(
    private val myCartRepo: MyCartRepo,
) : SuspendUseCase<UpdateCartDatabaseUseCase.Params, Unit> {

    data class Params(val product: Product)

    override suspend fun execute(params: Params) =
        myCartRepo.updateCart(params.product)

}
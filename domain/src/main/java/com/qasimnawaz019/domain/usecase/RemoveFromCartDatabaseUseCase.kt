package com.qasimnawaz019.domain.usecase

import com.qasimnawaz019.domain.repository.MyCartRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase

class RemoveFromCartDatabaseUseCase(
    private val myCartRepo: MyCartRepo,
) : SuspendUseCase<RemoveFromCartDatabaseUseCase.Params, Unit> {

    data class Params(val id: Int)

    override suspend fun execute(params: Params) =
        myCartRepo.removeCartById(params.id)

}
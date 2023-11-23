package com.qasimnawaz019.domain.usecase

import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.MyCartRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetCartDatabaseUseCase(
    private val myCartRepo: MyCartRepo,
    private val ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<Unit, @JvmSuppressWildcards Flow<List<Product>>> {
    override suspend fun execute(params: Unit): Flow<List<Product>> {
        return myCartRepo.getMyCarts().flowOn(ioDispatcher)
    }

}
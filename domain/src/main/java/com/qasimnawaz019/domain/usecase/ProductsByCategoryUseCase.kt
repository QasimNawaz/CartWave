package com.qasimnawaz019.domain.usecase

import com.qasimnawaz019.domain.model.ProductsByCategoryItem
import com.qasimnawaz019.domain.repository.ProductsRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ProductsByCategoryUseCase(
    private val productsRepository: ProductsRepo,
    private val ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<Unit, @JvmSuppressWildcards Flow<NetworkCall<List<ProductsByCategoryItem>>>> {
    override suspend fun execute(params: Unit): Flow<NetworkCall<List<ProductsByCategoryItem>>> {
        return productsRepository.getProductByCategory().flowOn(ioDispatcher)
    }


}
package com.qasimnawaz019.domain.usecase

import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.ProductsRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase
import com.qasimnawaz019.domain.utils.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ProductsUseCase(
    private val productsRepository: ProductsRepo,
    private val ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<ProductsUseCase.Params, @JvmSuppressWildcards Flow<ApiResponse<List<Product>>>> {

    data class Params(val limit: Int)

    override suspend fun execute(params: Params): Flow<ApiResponse<List<Product>>> {
        return productsRepository.getProducts(params.limit).flowOn(ioDispatcher)
    }


}
package com.qasimnawaz019.domain.usecase

import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.ProductsRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ProductUseCase(
    private val productsRepository: ProductsRepo,
    private val ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<ProductUseCase.Params, @JvmSuppressWildcards Flow<NetworkCall<Product>>> {

    data class Params(val productId: Int)

    override suspend fun execute(params: Params): Flow<NetworkCall<Product>> {
        return productsRepository.getProductDetail(params.productId).flowOn(ioDispatcher)
    }


}
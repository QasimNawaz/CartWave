package com.qasimnawaz019.domain.usecase

import androidx.paging.PagingData
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.ProductsRepo
import com.qasimnawaz019.domain.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow

class ProductsUseCase(
    private val productsRepository: ProductsRepo,
) : UseCase<Unit, @JvmSuppressWildcards Flow<PagingData<Product>>> {
    override fun execute(params: Unit): Flow<PagingData<Product>> {
        return productsRepository.getProducts()
    }

}
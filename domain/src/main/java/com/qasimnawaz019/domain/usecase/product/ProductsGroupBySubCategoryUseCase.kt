package com.qasimnawaz019.domain.usecase.product

import android.util.Log
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.ProductsByCategoryItem
import com.qasimnawaz019.domain.repository.ProductsRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ProductsGroupBySubCategoryUseCase(
    private val productsRepository: ProductsRepo,
    private val ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<ProductsGroupBySubCategoryUseCase.Params, @JvmSuppressWildcards Flow<NetworkCall<BaseResponse<List<ProductsByCategoryItem>>>>> {
    data class Params(val category: String)

    override suspend fun execute(params: Params): Flow<NetworkCall<BaseResponse<List<ProductsByCategoryItem>>>> {
        Log.d("ProductsGroupBySubCategoryUseCase", "getProductsGroupBySubCategory: ${params.category}")
        return productsRepository.getProductsGroupBySubCategory(params.category)
            .flowOn(ioDispatcher)
    }


}
package com.qasimnawaz019.domain.usecase

import com.qasimnawaz019.domain.repository.CategoriesRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase
import com.qasimnawaz019.domain.utils.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class CategoriesUseCase(
    private val caterRepository: CategoriesRepo,
    private val ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<Unit, @JvmSuppressWildcards Flow<ApiResponse<List<String>>>> {

    override suspend fun execute(params: Unit): @JvmSuppressWildcards Flow<ApiResponse<List<String>>> {
        return caterRepository.getCategories().flowOn(ioDispatcher)
    }
}
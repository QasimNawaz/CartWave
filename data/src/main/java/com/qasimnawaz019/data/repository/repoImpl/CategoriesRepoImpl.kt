package com.qasimnawaz019.data.repository.repoImpl

import com.qasimnawaz019.data.repository.dataSource.RemoteDataSource
import com.qasimnawaz019.domain.repository.CategoriesRepo
import com.qasimnawaz019.domain.utils.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CategoriesRepoImpl(
    private val remoteData: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : CategoriesRepo {
    override suspend fun getCategories(): Flow<ApiResponse<List<String>>> {
        return flow {
            emit(remoteData.getCategories())
        }.flowOn(ioDispatcher)
    }
}
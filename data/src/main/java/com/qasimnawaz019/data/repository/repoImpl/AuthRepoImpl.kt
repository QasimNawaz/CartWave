package com.qasimnawaz019.data.repository.repoImpl

import com.qasimnawaz019.data.repository.dataSource.RemoteDataSource
import com.qasimnawaz019.domain.dto.login.LoginRequestDto
import com.qasimnawaz019.domain.model.LoginResponse
import com.qasimnawaz019.domain.repository.AuthRepo
import com.qasimnawaz019.domain.utils.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepoImpl(
    private val remoteData: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : AuthRepo {
    override suspend fun login(
        requestDto: LoginRequestDto
    ): Flow<ApiResponse<LoginResponse>> {
        return flow {
            emit(remoteData.login(requestDto))
        }.flowOn(ioDispatcher)
    }
}
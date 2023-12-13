package com.qasimnawaz019.data.repository.repoImpl

import com.qasimnawaz019.data.repository.dataSource.RemoteDataSource
import com.qasimnawaz019.domain.dto.login.LoginRequestDto
import com.qasimnawaz019.domain.dto.login.RegisterRequestDto
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.User
import com.qasimnawaz019.domain.repository.AuthRepo
import com.qasimnawaz019.domain.utils.NetworkCall
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
    ): Flow<NetworkCall<BaseResponse<User>>> {
        return flow {
            emit(remoteData.login(requestDto))
        }.flowOn(ioDispatcher)
    }

    override suspend fun register(requestDto: RegisterRequestDto): Flow<NetworkCall<BaseResponse<User>>> {
        return flow {
            emit(remoteData.register(requestDto))
        }.flowOn(ioDispatcher)
    }
}
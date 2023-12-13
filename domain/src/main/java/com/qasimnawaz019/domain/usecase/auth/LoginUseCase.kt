package com.qasimnawaz019.domain.usecase.auth

import com.qasimnawaz019.domain.dto.login.LoginRequestDto
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.User
import com.qasimnawaz019.domain.repository.AuthRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class LoginUseCase(
    private val authRepository: AuthRepo,
    private val ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<LoginUseCase.Params, @JvmSuppressWildcards Flow<NetworkCall<BaseResponse<User>>>> {

    data class Params(val requestDto: LoginRequestDto)

    override suspend fun execute(params: Params): @JvmSuppressWildcards Flow<NetworkCall<BaseResponse<User>>> {
        return authRepository.login(params.requestDto).flowOn(ioDispatcher)
    }
}

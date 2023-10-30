package com.qasimnawaz019.domain.usecase

import com.qasimnawaz019.domain.dto.login.LoginRequestDto
import com.qasimnawaz019.domain.model.LoginResponse
import com.qasimnawaz019.domain.repository.AuthRepo
import com.qasimnawaz019.domain.usecase.base.SuspendUseCase
import com.qasimnawaz019.domain.utils.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class LoginUseCase(
    private val authRepository: AuthRepo,
    private val ioDispatcher: CoroutineDispatcher,
) : SuspendUseCase<LoginUseCase.Params, @JvmSuppressWildcards Flow<ApiResponse<LoginResponse>>> {

    data class Params(val requestDto: LoginRequestDto)

    override suspend fun execute(params: Params): @JvmSuppressWildcards Flow<ApiResponse<LoginResponse>> {
        return authRepository.login(params.requestDto).flowOn(ioDispatcher)
    }
}

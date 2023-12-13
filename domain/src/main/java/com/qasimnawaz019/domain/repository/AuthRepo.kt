package com.qasimnawaz019.domain.repository

import com.qasimnawaz019.domain.dto.login.LoginRequestDto
import com.qasimnawaz019.domain.dto.login.RegisterRequestDto
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.User
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    suspend fun login(requestDto: LoginRequestDto): Flow<NetworkCall<BaseResponse<User>>>
    suspend fun register(requestDto: RegisterRequestDto): Flow<NetworkCall<BaseResponse<User>>>
}
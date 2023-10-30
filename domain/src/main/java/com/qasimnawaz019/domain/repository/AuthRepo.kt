package com.qasimnawaz019.domain.repository

import com.qasimnawaz019.domain.dto.login.LoginRequestDto
import com.qasimnawaz019.domain.model.LoginResponse
import com.qasimnawaz019.domain.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    suspend fun login(requestDto: LoginRequestDto): Flow<ApiResponse<LoginResponse>>
}
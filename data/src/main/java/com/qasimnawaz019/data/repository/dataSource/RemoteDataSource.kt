package com.qasimnawaz019.data.repository.dataSource

import com.qasimnawaz019.domain.dto.login.LoginRequestDto
import com.qasimnawaz019.domain.model.LoginResponse
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun login(requestDto: LoginRequestDto): ApiResponse<LoginResponse>

    suspend fun getCategories(): ApiResponse<List<String>>

    suspend fun getProducts(limit: Int): ApiResponse<List<Product>>

    suspend fun getProductDetail(productId: Int): ApiResponse<Product>
}
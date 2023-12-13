package com.qasimnawaz019.data.repository.dataSource

import com.qasimnawaz019.domain.dto.login.LoginRequestDto
import com.qasimnawaz019.domain.dto.login.RegisterRequestDto
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.model.User
import com.qasimnawaz019.domain.utils.NetworkCall

interface RemoteDataSource {
    suspend fun login(requestDto: LoginRequestDto): NetworkCall<BaseResponse<User>>

    suspend fun register(requestDto: RegisterRequestDto): NetworkCall<BaseResponse<User>>

    suspend fun getProducts(limit: Int): NetworkCall<List<Product>>

    suspend fun getProductDetail(productId: Int): NetworkCall<Product>
}
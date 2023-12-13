package com.qasimnawaz019.data.repository.dataSourceImpl

import com.qasimnawaz019.data.repository.dataSource.RemoteDataSource
import com.qasimnawaz019.data.utils.LOGIN
import com.qasimnawaz019.data.utils.PRODUCTS
import com.qasimnawaz019.data.utils.REGISTER
import com.qasimnawaz019.data.utils.safeRequest
import com.qasimnawaz019.domain.dto.login.LoginRequestDto
import com.qasimnawaz019.domain.dto.login.RegisterRequestDto
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.model.User
import com.qasimnawaz019.domain.utils.NetworkCall
import com.qasimnawaz019.domain.utils.NetworkConnectivity
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType

class RemoteDataSourceImpl(
    private val client: HttpClient,
    private val networkConnectivity: NetworkConnectivity,
) : RemoteDataSource {
    override suspend fun login(requestDto: LoginRequestDto): NetworkCall<BaseResponse<User>> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Post
            url(LOGIN)
            contentType(ContentType.Application.Json)
            setBody(requestDto)
        }
    }

    override suspend fun register(requestDto: RegisterRequestDto): NetworkCall<BaseResponse<User>> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Post
            url(REGISTER)
            contentType(ContentType.Application.Json)
            setBody(requestDto)
        }
    }

    override suspend fun getProducts(limit: Int): NetworkCall<List<Product>> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Get
            url(PRODUCTS)
            parameter("limit", limit)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun getProductDetail(productId: Int): NetworkCall<Product> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Get
            url("$PRODUCTS/$productId")
            contentType(ContentType.Application.Json)
        }
    }

}
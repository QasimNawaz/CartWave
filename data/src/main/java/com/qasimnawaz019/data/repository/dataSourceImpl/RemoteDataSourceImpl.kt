package com.qasimnawaz019.data.repository.dataSourceImpl

import com.qasimnawaz019.data.repository.dataSource.RemoteDataSource
import com.qasimnawaz019.data.utils.CATEGORIES
import com.qasimnawaz019.data.utils.LOGIN
import com.qasimnawaz019.data.utils.safeRequest
import com.qasimnawaz019.domain.dto.login.LoginRequestDto
import com.qasimnawaz019.domain.model.LoginResponse
import com.qasimnawaz019.domain.utils.ApiResponse
import com.qasimnawaz019.domain.utils.NetworkConnectivity
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType

class RemoteDataSourceImpl(
    private val client: HttpClient,
    private val networkConnectivity: NetworkConnectivity,
) : RemoteDataSource {
    override suspend fun login(requestDto: LoginRequestDto): ApiResponse<LoginResponse> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Post
            url(LOGIN)
            contentType(ContentType.Application.Json)
            setBody(requestDto)
        }
    }

    override suspend fun getCategories(): ApiResponse<List<String>> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Get
            url(CATEGORIES)
            contentType(ContentType.Application.Json)
        }
    }

}
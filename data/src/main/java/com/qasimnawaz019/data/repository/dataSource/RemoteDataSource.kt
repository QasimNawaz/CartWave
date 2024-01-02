package com.qasimnawaz019.data.repository.dataSource

import com.qasimnawaz019.domain.dto.login.LoginRequestDto
import com.qasimnawaz019.domain.dto.login.RegisterRequestDto
import com.qasimnawaz019.domain.model.Address
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.model.ProductsByCategoryItem
import com.qasimnawaz019.domain.model.User
import com.qasimnawaz019.domain.utils.NetworkCall

interface RemoteDataSource {
    suspend fun login(requestDto: LoginRequestDto): NetworkCall<BaseResponse<User>>

    suspend fun register(requestDto: RegisterRequestDto): NetworkCall<BaseResponse<User>>

    suspend fun getProductDetail(productId: Int): NetworkCall<BaseResponse<Product>>

    suspend fun getProductsByCategory(): NetworkCall<BaseResponse<List<ProductsByCategoryItem>>>

    suspend fun getProductsGroupBySubCategory(category: String): NetworkCall<BaseResponse<List<ProductsByCategoryItem>>>

    suspend fun addToFavourite(productId: Int): NetworkCall<BaseResponse<String>>

    suspend fun removeFromFavourite(productId: Int): NetworkCall<BaseResponse<String>>

    suspend fun getUserCart(): NetworkCall<BaseResponse<List<Product>>>
    suspend fun addToCart(productId: Int, cartQty: Int): NetworkCall<BaseResponse<String>>

    suspend fun removeFromCart(productId: Int): NetworkCall<BaseResponse<String>>

    suspend fun addAddress(address: String): NetworkCall<BaseResponse<String>>

    suspend fun updatePrimaryAddress(addressId: Int): NetworkCall<BaseResponse<String>>

    suspend fun getPrimaryAddress(): NetworkCall<BaseResponse<Address>>

    suspend fun getAddresses(): NetworkCall<BaseResponse<List<Address>>>
}
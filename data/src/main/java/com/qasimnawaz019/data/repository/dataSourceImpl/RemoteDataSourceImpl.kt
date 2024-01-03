package com.qasimnawaz019.data.repository.dataSourceImpl

import android.util.Log
import com.qasimnawaz019.data.repository.dataSource.RemoteDataSource
import com.qasimnawaz019.data.repository.datastore.DataStoreRepository
import com.qasimnawaz019.data.utils.ADD_ADDRESS
import com.qasimnawaz019.data.utils.ADD_TO_CART
import com.qasimnawaz019.data.utils.ADD_TO_FAVOURITE
import com.qasimnawaz019.data.utils.GET_ADDRESSES
import com.qasimnawaz019.data.utils.GET_PRIMARY_ADDRESS
import com.qasimnawaz019.data.utils.GET_PRODUCT
import com.qasimnawaz019.data.utils.GET_USER_CART
import com.qasimnawaz019.data.utils.LOGIN
import com.qasimnawaz019.data.utils.PLACE_ORDER
import com.qasimnawaz019.data.utils.PRODUCTS_BY_CATEGORY
import com.qasimnawaz019.data.utils.PRODUCTS_GROUP_BY_CATEGORY
import com.qasimnawaz019.data.utils.REGISTER
import com.qasimnawaz019.data.utils.REMOVE_FROM_CART
import com.qasimnawaz019.data.utils.REMOVE_FROM_FAVOURITE
import com.qasimnawaz019.data.utils.UPDATE_PRIMARY_ADDRESS
import com.qasimnawaz019.data.utils.safeRequest
import com.qasimnawaz019.domain.dto.address.AddAddressRequestDto
import com.qasimnawaz019.domain.dto.address.UpdatePrimaryAddressRequestDto
import com.qasimnawaz019.domain.dto.cart.AddToCartRequestDto
import com.qasimnawaz019.domain.dto.favourite.AddToFavouriteRequestDto
import com.qasimnawaz019.domain.dto.login.LoginRequestDto
import com.qasimnawaz019.domain.dto.login.RegisterRequestDto
import com.qasimnawaz019.domain.dto.order.PlaceOrderRequestDto
import com.qasimnawaz019.domain.model.Address
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.model.ProductsByCategoryItem
import com.qasimnawaz019.domain.model.User
import com.qasimnawaz019.domain.utils.NetworkCall
import com.qasimnawaz019.domain.utils.NetworkConnectivity
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.flow.first

class RemoteDataSourceImpl(
    private val client: HttpClient,
    private val networkConnectivity: NetworkConnectivity,
    private val dataStoreRepository: DataStoreRepository
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

    override suspend fun getProductDetail(productId: Int): NetworkCall<BaseResponse<Product>> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Get
            url(GET_PRODUCT)
            header("Authorization", "Bearer ${dataStoreRepository.readAccessToken().first()}")
            parameter("userId", dataStoreRepository.getUser().first()?.id)
            parameter("productId", productId)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun getProductsByCategory(): NetworkCall<BaseResponse<List<ProductsByCategoryItem>>> {
        Log.d("RemoteDataSourceImpl", "getProductsByCategory")
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Get
            url(PRODUCTS_BY_CATEGORY)
            header("Authorization", "Bearer ${dataStoreRepository.readAccessToken().first()}")
            parameter("userId", dataStoreRepository.getUser().first()?.id)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun getProductsGroupBySubCategory(category: String): NetworkCall<BaseResponse<List<ProductsByCategoryItem>>> {
        Log.d("RemoteDataSourceImpl", "getProductsGroupBySubCategory: $category")
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Get
            url(PRODUCTS_GROUP_BY_CATEGORY)
            header("Authorization", "Bearer ${dataStoreRepository.readAccessToken().first()}")
            parameter("userId", dataStoreRepository.getUser().first()?.id)
            parameter("category", category)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun addToFavourite(productId: Int): NetworkCall<BaseResponse<String>> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Post
            url(ADD_TO_FAVOURITE)
            header("Authorization", "Bearer ${dataStoreRepository.readAccessToken().first()}")
            contentType(ContentType.Application.Json)
            setBody(
                AddToFavouriteRequestDto(
                    dataStoreRepository.getUser().first()?.id ?: 0,
                    productId
                )
            )
        }
    }

    override suspend fun removeFromFavourite(productId: Int): NetworkCall<BaseResponse<String>> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Post
            url(REMOVE_FROM_FAVOURITE)
            header("Authorization", "Bearer ${dataStoreRepository.readAccessToken().first()}")
            contentType(ContentType.Application.Json)
            setBody(
                AddToFavouriteRequestDto(
                    dataStoreRepository.getUser().first()?.id ?: 0,
                    productId
                )
            )
        }
    }

    override suspend fun getUserCart(): NetworkCall<BaseResponse<List<Product>>> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Get
            url(GET_USER_CART)
            header("Authorization", "Bearer ${dataStoreRepository.readAccessToken().first()}")
            parameter("userId", dataStoreRepository.getUser().first()?.id)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun addToCart(
        productId: Int,
        cartQty: Int
    ): NetworkCall<BaseResponse<String>> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Post
            url(ADD_TO_CART)
            header("Authorization", "Bearer ${dataStoreRepository.readAccessToken().first()}")
            contentType(ContentType.Application.Json)
            setBody(
                AddToCartRequestDto(
                    dataStoreRepository.getUser().first()?.id ?: 0,
                    productId,
                    cartQty
                )
            )
        }
    }

    override suspend fun removeFromCart(productId: Int): NetworkCall<BaseResponse<String>> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Post
            url(REMOVE_FROM_CART)
            header("Authorization", "Bearer ${dataStoreRepository.readAccessToken().first()}")
            contentType(ContentType.Application.Json)
            setBody(
                AddToCartRequestDto(
                    dataStoreRepository.getUser().first()?.id ?: 0,
                    productId,
                    0
                )
            )
        }
    }

    override suspend fun addAddress(address: String): NetworkCall<BaseResponse<String>> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Post
            url(ADD_ADDRESS)
            header("Authorization", "Bearer ${dataStoreRepository.readAccessToken().first()}")
            contentType(ContentType.Application.Json)
            setBody(
                AddAddressRequestDto(
                    dataStoreRepository.getUser().first()?.id ?: 0,
                    address
                )
            )
        }
    }

    override suspend fun updatePrimaryAddress(addressId: Int): NetworkCall<BaseResponse<String>> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Post
            url(UPDATE_PRIMARY_ADDRESS)
            header("Authorization", "Bearer ${dataStoreRepository.readAccessToken().first()}")
            contentType(ContentType.Application.Json)
            setBody(
                UpdatePrimaryAddressRequestDto(
                    dataStoreRepository.getUser().first()?.id ?: 0,
                    addressId
                )
            )
        }
    }

    override suspend fun getPrimaryAddress(): NetworkCall<BaseResponse<Address>> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Get
            url(GET_PRIMARY_ADDRESS)
            header("Authorization", "Bearer ${dataStoreRepository.readAccessToken().first()}")
            parameter("userId", dataStoreRepository.getUser().first()?.id)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun getAddresses(): NetworkCall<BaseResponse<List<Address>>> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Get
            url(GET_ADDRESSES)
            header("Authorization", "Bearer ${dataStoreRepository.readAccessToken().first()}")
            parameter("userId", dataStoreRepository.getUser().first()?.id)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun placeOrder(placeOrderRequestDto: PlaceOrderRequestDto): NetworkCall<BaseResponse<String>> {
        return client.safeRequest(networkConnectivity) {
            method = HttpMethod.Post
            url(PLACE_ORDER)
            header("Authorization", "Bearer ${dataStoreRepository.readAccessToken().first()}")
            contentType(ContentType.Application.Json)
            setBody(
                placeOrderRequestDto.copy(userId = dataStoreRepository.getUser().first()?.id ?: 0)
            )
        }
    }

}
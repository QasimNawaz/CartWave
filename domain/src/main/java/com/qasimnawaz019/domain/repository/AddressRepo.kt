package com.qasimnawaz019.domain.repository

import com.qasimnawaz019.domain.model.Address
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.flow.Flow

interface AddressRepo {
    suspend fun addAddress(address: String): Flow<NetworkCall<BaseResponse<String>>>

    suspend fun updatePrimaryAddress(addressId: Int): Flow<NetworkCall<BaseResponse<String>>>

    suspend fun getPrimaryAddress(): Flow<NetworkCall<BaseResponse<Address>>>

    suspend fun getAddresses(): Flow<NetworkCall<BaseResponse<List<Address>>>>
}
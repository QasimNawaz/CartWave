package com.qasimnawaz019.data.repository.repoImpl

import com.qasimnawaz019.data.repository.dataSource.RemoteDataSource
import com.qasimnawaz019.domain.model.Address
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.repository.AddressRepo
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AddressRepoImpl(
    private val remoteData: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : AddressRepo {
    override suspend fun addAddress(address: String): Flow<NetworkCall<BaseResponse<String>>> {
        return flow {
            emit(remoteData.addAddress(address))
        }.flowOn(ioDispatcher)
    }

    override suspend fun updatePrimaryAddress(addressId: Int): Flow<NetworkCall<BaseResponse<String>>> {
        return flow {
            emit(remoteData.updatePrimaryAddress(addressId))
        }.flowOn(ioDispatcher)
    }

    override suspend fun getPrimaryAddress(): Flow<NetworkCall<BaseResponse<Address>>> {
        return flow {
            emit(remoteData.getPrimaryAddress())
        }.flowOn(ioDispatcher)
    }

    override suspend fun getAddresses(): Flow<NetworkCall<BaseResponse<List<Address>>>> {
        return flow {
            emit(remoteData.getAddresses())
        }.flowOn(ioDispatcher)
    }
}
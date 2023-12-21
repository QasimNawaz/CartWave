package com.qasimnawaz019.data.repository.repoImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.qasimnawaz019.data.repository.dataSource.FavouritesPagingSourceFactory
import com.qasimnawaz019.data.repository.dataSource.RemoteDataSource
import com.qasimnawaz019.domain.model.BaseResponse
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.FavouriteProductsRepo
import com.qasimnawaz019.domain.utils.NetworkCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FavouriteProductsRepoImpl(
    private val remoteData: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
    private val favouritesPagingSourceFactory: FavouritesPagingSourceFactory
) : FavouriteProductsRepo {

    override suspend fun addToFavourite(productId: Int): Flow<NetworkCall<BaseResponse<String>>> {
        return flow {
            emit(remoteData.addToFavourite(productId))
        }.flowOn(ioDispatcher)
    }

    override suspend fun removeFromFavourite(productId: Int): Flow<NetworkCall<BaseResponse<String>>> {
        return flow {
            emit(remoteData.removeFromFavourite(productId))
        }.flowOn(ioDispatcher)
    }

    override fun getFavouritesPaging(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = favouritesPagingSourceFactory
        ).flow.flowOn(ioDispatcher)
    }
}
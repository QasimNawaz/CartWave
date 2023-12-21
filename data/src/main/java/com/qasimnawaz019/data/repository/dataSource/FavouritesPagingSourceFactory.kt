package com.qasimnawaz019.data.repository.dataSource

import androidx.paging.PagingSource
import com.qasimnawaz019.data.repository.datastore.DataStoreRepository
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.utils.NetworkConnectivity
import io.ktor.client.HttpClient

class FavouritesPagingSourceFactory(
    private val client: HttpClient,
    private val networkConnectivity: NetworkConnectivity,
    private val dataStoreRepository: DataStoreRepository
) : () -> PagingSource<Int, Product> {
    override fun invoke(): PagingSource<Int, Product> {
        return FavouritesPagingSource(client, networkConnectivity, dataStoreRepository)
    }
}
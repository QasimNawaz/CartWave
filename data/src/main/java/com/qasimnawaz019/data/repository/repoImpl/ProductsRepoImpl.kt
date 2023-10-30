package com.qasimnawaz019.data.repository.repoImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.qasimnawaz019.data.database.dao.FavouriteProductsDao
import com.qasimnawaz019.data.repository.dataSource.ProductsPagingSource
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.ProductsRepo
import com.qasimnawaz019.domain.utils.NetworkConnectivity
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow

class ProductsRepoImpl(
    private val client: HttpClient,
    private val productsDao: FavouriteProductsDao,
    private val networkConnectivity: NetworkConnectivity,
) : ProductsRepo {

    override fun getProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                ProductsPagingSource(
                    client = client,
                    productsDao = productsDao,
                    networkConnectivity = networkConnectivity
                )
            },
        ).flow
    }
}
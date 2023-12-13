package com.qasimnawaz019.data.repository.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.qasimnawaz019.data.database.dao.FavouriteProductsDao
import com.qasimnawaz019.data.utils.PRODUCTS
import com.qasimnawaz019.data.utils.safeRequest
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.utils.NetworkCall
import com.qasimnawaz019.domain.utils.NetworkConnectivity
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType

class ProductsPagingSource(
    private val client: HttpClient,
    private val productsDao: FavouriteProductsDao,
    private val networkConnectivity: NetworkConnectivity
) : PagingSource<Int, Product>() {


    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val page = params.key ?: 1
            val response = client.safeRequest<List<Product>>(networkConnectivity) {
                method = HttpMethod.Get
                url(PRODUCTS)
                parameter("limit", page * 10)
                contentType(ContentType.Application.Json)
            }
            when (response) {
                is NetworkCall.Error.GenericError -> LoadResult.Error(Throwable(response.message))
                is NetworkCall.Error.HttpError -> LoadResult.Error(Throwable(response.message))
                is NetworkCall.Error.NoNetwork -> LoadResult.Error(Throwable(response.cause.message))
                is NetworkCall.Error.SerializationError -> LoadResult.Error(Throwable(response.message))
                is NetworkCall.Success -> {
                    val remoteProducts = response.data
                    productsDao.getFavouriteEntities().collect { collections ->
                        val cacheProducts = collections.associateBy { it.id }
                        remoteProducts.onEach { product ->
                            product.isFavourite =
                                cacheProducts.containsKey(product.id)
                        }
                    }
//                    remoteProducts.onEach { product ->
//                        product.isFavourite = cacheProducts.containsKey(product.id)
//                    }
                    LoadResult.Page(
                        data = remoteProducts,
                        prevKey = if (page == 1) null else page.minus(1),
                        nextKey = if (response.data.isEmpty()) null else page.plus(
                            1
                        ),
                    )

                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
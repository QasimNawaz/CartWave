package com.qasimnawaz019.data.repository.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.qasimnawaz019.data.repository.datastore.DataStoreRepository
import com.qasimnawaz019.data.utils.GET_FAVOURITES
import com.qasimnawaz019.data.utils.safeRequest
import com.qasimnawaz019.domain.model.PagingBaseResponse
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.utils.NetworkCall
import com.qasimnawaz019.domain.utils.NetworkConnectivity
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.flow.first

class FavouritesPagingSource(
    private val client: HttpClient,
    private val networkConnectivity: NetworkConnectivity,
    private val dataStoreRepository: DataStoreRepository
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
            val response =
                client.safeRequest<PagingBaseResponse<List<Product>>>(networkConnectivity) {
                    method = HttpMethod.Get
                    url(GET_FAVOURITES)
                    header(
                        "Authorization",
                        "Bearer ${dataStoreRepository.readAccessToken().first()}"
                    )
                    parameter("pageNumber", page)
                    parameter("pageSize", 10)
                    parameter("userId", dataStoreRepository.getUser().first()?.id)
                    contentType(ContentType.Application.Json)
                }
            when (response) {
                is NetworkCall.Error.GenericError -> LoadResult.Error(Throwable(response.message))
                is NetworkCall.Error.HttpError -> LoadResult.Error(Throwable(response.message))
                is NetworkCall.Error.NoNetwork -> LoadResult.Error(Throwable(response.cause.message))
                is NetworkCall.Error.SerializationError -> LoadResult.Error(Throwable(response.message))
                is NetworkCall.Success -> {
                    if (response.data.success == true) {
                        val favourites = response.data.data
                        LoadResult.Page(
                            data = favourites ?: emptyList(),
                            prevKey = if (page == 1) null else page.minus(1),
                            nextKey = if (response.data.data?.isEmpty() == true) null else page.plus(
                                1
                            ),
                        )
                    } else {
                        LoadResult.Error(Throwable(response.data.message))
                    }

                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
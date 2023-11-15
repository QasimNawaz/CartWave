package com.qasimnawaz019.data.repository.repoImpl

import android.util.Log
import com.qasimnawaz019.data.database.dao.FavouriteProductsDao
import com.qasimnawaz019.data.database.dao.MyCartProductDao
import com.qasimnawaz019.data.repository.dataSource.RemoteDataSource
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.repository.ProductsRepo
import com.qasimnawaz019.domain.utils.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductsRepoImpl(
    private val remoteData: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
    private val favouriteProductsDao: FavouriteProductsDao,
    private val myCartProductDao: MyCartProductDao
) : ProductsRepo {

    override suspend fun getProducts(limit: Int): Flow<ApiResponse<List<Product>>> {
        return flow {
            val remoteProducts = remoteData.getProducts(limit)
            val cacheProducts = favouriteProductsDao.getFavouriteEntities().associateBy { it.id }
            when (remoteProducts) {
                is ApiResponse.Success -> {
                    remoteProducts.body.onEach { product ->
                        product.isFavourite = cacheProducts.containsKey(product.id)
                    }
                    emit(remoteProducts)
                }

                else -> {
                    emit(remoteProducts)
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getProductDetail(productId: Int): Flow<ApiResponse<Product>> {
        return flow {
            val remoteProduct = remoteData.getProductDetail(productId)
            val cacheFavouriteProduct = favouriteProductsDao.getFavouriteById(productId)
            val cacheCartProduct = myCartProductDao.getCartById(productId)
            when (remoteProduct) {
                is ApiResponse.Success -> {
                    Log.d("ProductDetailScr", "cacheFavouriteProduct: $cacheFavouriteProduct")
                    Log.d("ProductDetailScr", "cacheCartProduct: $cacheCartProduct")
                    if (cacheFavouriteProduct != null) {
                        remoteProduct.body.isFavourite = true
                    }
                    cacheCartProduct?.let {
                        remoteProduct.body.cartQty = it.cartQty
                    }
                    emit(remoteProduct)
                }

                else -> {
                    emit(remoteProduct)
                }
            }
        }.flowOn(ioDispatcher)
    }

}
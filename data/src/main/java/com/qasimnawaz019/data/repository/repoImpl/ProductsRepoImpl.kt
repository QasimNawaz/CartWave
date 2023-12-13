package com.qasimnawaz019.data.repository.repoImpl

import com.qasimnawaz019.data.database.dao.FavouriteProductsDao
import com.qasimnawaz019.data.database.dao.MyCartProductDao
import com.qasimnawaz019.data.repository.dataSource.RemoteDataSource
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.model.ProductsByCategoryItem
import com.qasimnawaz019.domain.repository.ProductsRepo
import com.qasimnawaz019.domain.utils.NetworkCall
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

    override suspend fun getProducts(limit: Int): Flow<NetworkCall<List<Product>>> {
        return flow {
            when (val remoteProducts = remoteData.getProducts(limit)) {
                is NetworkCall.Success -> {
                    favouriteProductsDao.getFavouriteEntities().collect { collections ->
                        val cacheProducts = collections.associateBy { it.id }
                        remoteProducts.data.onEach { product ->
                            product.isFavourite = cacheProducts.containsKey(product.id)
                        }
                        emit(remoteProducts)
                    }
                }

                else -> {
                    emit(remoteProducts)
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getProductDetail(productId: Int): Flow<NetworkCall<Product>> {
        return flow {
            val remoteProduct = remoteData.getProductDetail(productId)
            val cacheFavouriteProduct = favouriteProductsDao.getFavouriteById(productId)
            val cacheCartProduct = myCartProductDao.getCartById(productId)
            when (remoteProduct) {
                is NetworkCall.Success -> {
                    if (cacheFavouriteProduct != null) {
                        remoteProduct.data.isFavourite = true
                    }
                    cacheCartProduct?.let {
                        remoteProduct.data.cartQty = it.cartQty
                    }
                    emit(remoteProduct)
                }

                else -> {
                    emit(remoteProduct)
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getProductByCategory(): Flow<NetworkCall<List<ProductsByCategoryItem>>> {
        TODO("Not yet implemented")
    }

}
package com.qasimnawaz019.data.repository.dataSourceImpl

import com.qasimnawaz019.data.database.dao.FavouriteProductsDao
import com.qasimnawaz019.data.database.dao.MyCartProductDao
import com.qasimnawaz019.data.database.entities.FavouriteProductEntity
import com.qasimnawaz019.data.database.entities.MyCartProductEntity
import com.qasimnawaz019.data.database.entities.RatingEntity
import com.qasimnawaz019.data.repository.dataSource.LocalDataSource
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.model.Rating
import java.util.stream.Collectors

class LocalDataSourceImpl(
    private val favouriteProductsDao: FavouriteProductsDao,
    private val myCartProductDao: MyCartProductDao
) : LocalDataSource {
    override suspend fun getFavouriteProducts(): List<Product> {
        return favouriteProductsDao.getFavouriteEntities().stream().map { entity ->
            Product(
                image = entity.image,
                price = entity.price,
                rating = entity?.rating?.let { Rating(it.rate, it.count) },
                description = entity.description,
                id = entity.id,
                title = entity.title,
                category = entity.category,
                isFavourite = entity.isFavourite
            )
        }.collect(Collectors.toList())
    }

    override suspend fun addToFavouriteProduct(product: Product) {
        with(product) {
            favouriteProductsDao.addFavouriteEntity(
                FavouriteProductEntity(
                    image = image,
                    price = price,
                    rating = rating?.let { RatingEntity(it.rate, it.count) },
                    description = description,
                    id = id,
                    title = title,
                    category = category,
                    isFavourite = isFavourite.not()
                )
            )
        }
    }

    override suspend fun removeFavouriteById(id: Int) {
        favouriteProductsDao.deleteById(id)
    }

    override suspend fun getMyCarts(): List<Product> {
        return myCartProductDao.getMyCartEntities().stream().map { entity ->
            Product(
                image = entity.image,
                price = entity.price,
                rating = entity?.rating?.let { Rating(it.rate, it.count) },
                description = entity.description,
                id = entity.id,
                title = entity.title,
                category = entity.category,
                isFavourite = false
            )
        }.collect(Collectors.toList())
    }

    override suspend fun addToCart(product: Product) {
        with(product) {
            myCartProductDao.addCartEntity(
                MyCartProductEntity(
                    image = image,
                    price = price,
                    rating = rating?.let { RatingEntity(it.rate, it.count) },
                    description = description,
                    id = id,
                    title = title,
                    category = category,
                    cartQty = cartQty
                )
            )
        }
    }

    override suspend fun removeCartById(id: Int) {
        myCartProductDao.deleteById(id)
    }
}
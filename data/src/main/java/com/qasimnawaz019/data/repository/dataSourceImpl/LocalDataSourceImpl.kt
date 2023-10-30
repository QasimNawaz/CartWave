package com.qasimnawaz019.data.repository.dataSourceImpl

import com.qasimnawaz019.data.database.dao.FavouriteProductsDao
import com.qasimnawaz019.data.database.entities.ProductEntity
import com.qasimnawaz019.data.database.entities.RatingEntity
import com.qasimnawaz019.data.repository.dataSource.LocalDataSource
import com.qasimnawaz019.domain.model.Product
import com.qasimnawaz019.domain.model.Rating
import java.util.stream.Collectors

class LocalDataSourceImpl(
    private val productsDao: FavouriteProductsDao
) : LocalDataSource {
    override suspend fun getFavouriteProducts(): List<Product> {
        return productsDao.getFavouriteEntities().stream().map { entity ->
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
            productsDao.addFavouriteEntity(
                ProductEntity(
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
        productsDao.deleteById(id)
    }
}
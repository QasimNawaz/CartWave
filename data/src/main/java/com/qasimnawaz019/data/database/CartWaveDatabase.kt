package com.qasimnawaz019.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.qasimnawaz019.data.database.converter.RatingTypeConverter
import com.qasimnawaz019.data.database.dao.FavouriteProductsDao
import com.qasimnawaz019.data.database.entities.ProductEntity

@Database(
    entities = [ProductEntity::class], version = 1
)
@TypeConverters(RatingTypeConverter::class)
abstract class CartWaveDatabase : RoomDatabase() {
    abstract fun favouriteProductsDao(): FavouriteProductsDao
}
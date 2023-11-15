package com.qasimnawaz019.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.qasimnawaz019.data.database.converter.RatingTypeConverter
import com.qasimnawaz019.data.database.dao.FavouriteProductsDao
import com.qasimnawaz019.data.database.dao.MyCartProductDao
import com.qasimnawaz019.data.database.entities.FavouriteProductEntity
import com.qasimnawaz019.data.database.entities.MyCartProductEntity

@Database(
    entities = [FavouriteProductEntity::class, MyCartProductEntity::class], version = 1
)
@TypeConverters(RatingTypeConverter::class)
abstract class CartWaveDatabase : RoomDatabase() {
    abstract fun favouriteProductsDao(): FavouriteProductsDao
    abstract fun myCartDao(): MyCartProductDao
}
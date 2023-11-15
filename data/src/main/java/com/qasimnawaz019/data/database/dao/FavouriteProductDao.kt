package com.qasimnawaz019.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qasimnawaz019.data.database.entities.FavouriteProductEntity

@Dao
interface FavouriteProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteEntity(favouriteEntity: FavouriteProductEntity)

    @Query(value = "SELECT * FROM FAVOURITE_PRODUCT_TABLE")
    suspend fun getFavouriteEntities(): List<FavouriteProductEntity>

    @Query("SELECT * FROM FAVOURITE_PRODUCT_TABLE WHERE id = :id")
    suspend fun getFavouriteById(id: Int): FavouriteProductEntity?

    @Query("DELETE FROM FAVOURITE_PRODUCT_TABLE WHERE id = :id")
    suspend fun deleteById(id: Int)
}
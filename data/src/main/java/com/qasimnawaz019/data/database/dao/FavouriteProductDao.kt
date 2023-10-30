package com.qasimnawaz019.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.qasimnawaz019.data.database.entities.ProductEntity

@Dao
interface FavouriteProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteEntity(favouriteEntity: ProductEntity)

    @Query(value = "SELECT * FROM FAVOURITE_PRODUCT_TABLE")
    suspend fun getFavouriteEntities(): List<ProductEntity>

    @Query("DELETE FROM FAVOURITE_PRODUCT_TABLE WHERE id = :id")
    suspend fun deleteById(id: Int)
}
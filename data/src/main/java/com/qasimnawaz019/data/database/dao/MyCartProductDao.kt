package com.qasimnawaz019.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qasimnawaz019.data.database.entities.MyCartProductEntity

@Dao
interface MyCartProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCartEntity(cartProductEntity: MyCartProductEntity)

    @Query(value = "SELECT * FROM MY_CART_TABLE")
    suspend fun getMyCartEntities(): List<MyCartProductEntity>

    @Query("SELECT * FROM MY_CART_TABLE WHERE id = :id")
    suspend fun getCartById(id: Int): MyCartProductEntity?

    @Query("DELETE FROM MY_CART_TABLE WHERE id = :id")
    suspend fun deleteById(id: Int)
}
package com.qasimnawaz019.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.qasimnawaz019.data.database.converter.RatingTypeConverter
import com.qasimnawaz019.data.utils.MY_CART_TABLE
import kotlinx.serialization.Serializable
@Entity(tableName = MY_CART_TABLE)
@Serializable
data class MyCartProductEntity(

    @ColumnInfo("image")
    val image: String? = null,

    @ColumnInfo("price")
    val price: Double? = null,

    @ColumnInfo("rating")
    @TypeConverters(RatingTypeConverter::class)
    val rating: RatingEntity? = null,

    @ColumnInfo("description")
    val description: String? = null,

    @PrimaryKey
    @ColumnInfo("id")
    val id: Int? = null,

    @ColumnInfo("title")
    val title: String? = null,

    @ColumnInfo("category")
    val category: String? = null,

    @ColumnInfo("cartQty")
    val cartQty: Int = 0
)
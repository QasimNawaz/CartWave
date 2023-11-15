package com.qasimnawaz019.data.database.entities

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class RatingEntity(

    @ColumnInfo("rate")
    val rate: Double? = null,

    @ColumnInfo("count")
    val count: Int? = null
)
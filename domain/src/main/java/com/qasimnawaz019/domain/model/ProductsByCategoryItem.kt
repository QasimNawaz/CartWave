package com.qasimnawaz019.domain.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProductsByCategoryItem(
    @SerializedName("category")
    val category: String?,
    @SerializedName("products")
    val products: List<Product>?
)
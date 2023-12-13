package com.qasimnawaz019.domain.model


import com.google.gson.annotations.SerializedName

data class ProductsByCategoryItem(
    @SerializedName("category")
    val category: String?,
    @SerializedName("products")
    val products: List<ProductX>?
)
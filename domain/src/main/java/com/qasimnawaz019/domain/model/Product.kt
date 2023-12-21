package com.qasimnawaz019.domain.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerializedName("actualPrice")
    val actualPrice: String?,
    @SerializedName("averageRating")
    val averageRating: String?,
    @SerializedName("brand")
    val brand: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("crawledAt")
    val crawledAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("discount")
    val discount: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("images")
    val images: List<String> = emptyList(),
    @SerializedName("isFavourite")
    val isFavourite: Boolean = false,
    @SerializedName("outOfStock")
    val outOfStock: Boolean?,
    @SerializedName("pid")
    val pid: String?,
    @SerializedName("productDetails")
    val productDetails: List<Map<String, String>>?,
    @SerializedName("seller")
    val seller: String?,
    @SerializedName("sellingPrice")
    val sellingPrice: String?,
    @SerializedName("subCategory")
    val subCategory: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("cartQty")
    val cartQty: Int = 0,
)
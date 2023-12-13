package com.qasimnawaz019.cartwave.ui.screens.splash


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerializedName("actual_price")
    val actual_price: String?,
    @SerializedName("average_rating")
    val average_rating: String?,
    @SerializedName("brand")
    val brand: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("crawled_at")
    val crawled_at: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("discount")
    val discount: String?,
    @SerializedName("_id")
    val _id: String?,
    @SerializedName("images")
    val images: List<String?>?,
    @SerializedName("out_of_stock")
    val out_of_stock: Boolean?,
    @SerializedName("pid")
    val pid: String?,
    @SerializedName("product_details")
    val product_details: List<Map<String, String>?>?,
    @SerializedName("seller")
    val seller: String?,
    @SerializedName("selling_price")
    val selling_price: String?,
    @SerializedName("sub_category")
    val sub_category: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?
)
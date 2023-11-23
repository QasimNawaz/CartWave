package com.qasimnawaz019.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    var image: String? = null,

    var price: Double = 0.0,

    var rating: Rating? = null,

    var description: String? = null,

    var id: Int? = null,

    var title: String? = null,

    var category: String? = null,

    var isFavourite: Boolean = false,

    var cartQty: Int = 0
)

@Serializable
data class Rating(
    var rate: Double? = null,

    var count: Int? = null
)
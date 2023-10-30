package com.qasimnawaz019.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val image: String? = null,

    val price: Double? = null,

    val rating: Rating? = null,

    val description: String? = null,

    val id: Int? = null,

    val title: String? = null,

    val category: String? = null,

    var isFavourite: Boolean = false
)

@Serializable
data class Rating(
    val rate: Double? = null,

    val count: Int? = null
)
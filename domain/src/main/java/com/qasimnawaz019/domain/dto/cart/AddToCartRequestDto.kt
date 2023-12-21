package com.qasimnawaz019.domain.dto.cart

@kotlinx.serialization.Serializable
data class AddToCartRequestDto(
    val userId: Int,
    val productId: Int,
    val cartQty: Int
)

package com.qasimnawaz019.domain.dto.favourite

@kotlinx.serialization.Serializable
data class AddToFavouriteRequestDto(
    val userId: Int,
    val productId: Int,
)

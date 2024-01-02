package com.qasimnawaz019.domain.dto.address

@kotlinx.serialization.Serializable
data class AddAddressRequestDto(
    val userId: Int,
    val address: String,
)

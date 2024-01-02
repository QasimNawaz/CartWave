package com.qasimnawaz019.domain.dto.address

@kotlinx.serialization.Serializable
data class UpdatePrimaryAddressRequestDto(
    val userId: Int,
    val addressId: Int,
)

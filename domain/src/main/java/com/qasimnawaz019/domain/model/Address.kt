package com.qasimnawaz019.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    @SerializedName("id")
    val id: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("address")
    val address: String,
    @SerializedName("isPrimary")
    var isPrimary: Boolean
)

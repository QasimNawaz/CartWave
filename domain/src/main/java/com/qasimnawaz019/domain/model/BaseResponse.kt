package com.qasimnawaz019.domain.model

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class BaseResponse<T>(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: T?,
    @SerializedName("message")
    val message: String
)

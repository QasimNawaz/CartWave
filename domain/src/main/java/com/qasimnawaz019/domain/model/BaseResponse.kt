package com.qasimnawaz019.domain.model

@kotlinx.serialization.Serializable
data class BaseResponse<T>(
    val success: Boolean,
    val data: T?,
    val message: String
)

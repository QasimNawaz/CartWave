package com.qasimnawaz019.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String? = null
)
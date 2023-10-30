package com.qasimnawaz019.domain.dto.login

@kotlinx.serialization.Serializable
data class LoginRequestDto(
    val username: String? = null,
    val password: String? = null
)
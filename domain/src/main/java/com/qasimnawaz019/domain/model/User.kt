package com.qasimnawaz019.domain.model


@kotlinx.serialization.Serializable
data class User(
    val authToken: String?,
    val avatar: String?,
    val createdAt: String?,
    val email: String?,
    val firstName: String?,
    val id: Int?,
    val lastName: String?
)
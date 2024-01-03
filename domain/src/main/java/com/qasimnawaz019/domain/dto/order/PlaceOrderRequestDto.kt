package com.qasimnawaz019.domain.dto.order

@kotlinx.serialization.Serializable
data class PlaceOrderRequestDto(
    val userId: Int,
    val orderDate: String,
    val shippingAddress: String,
    val promoCode: String,
    val orderStatus: String,
    val totalAmount: Int,
    val paymentMethod: String,
    val products: List<OrderProduct>
)

@kotlinx.serialization.Serializable
data class OrderProduct(
    val productId: Int,
    val title: String,
    val price: Int,
    val quantity: Int
)

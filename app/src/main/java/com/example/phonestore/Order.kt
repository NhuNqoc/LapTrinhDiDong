package com.example.phonestore

data class Order(
    val id: String = "",
    val userId: String = "",
    val customerName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val paymentMethod: String = "",
    val totalPrice: Long = 0L,
    val status: String = "PENDING",
    val createdAt: Long = 0L,
    val items: List<OrderItem> = emptyList()
)

data class OrderItem(
    val name: String = "",
    val price: String = "",
    val quantity: Int = 0
)
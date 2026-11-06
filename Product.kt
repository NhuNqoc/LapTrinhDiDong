package com.example.phonestoreapp.model

data class Product(
    val id: String = "",
    val name: String = "",          // Tên điện thoại (VD: iPhone 15 Pro Max)
    val price: Double = 0.0,        // Giá bán
    val brand: String = "",        // Hãng (Apple, Samsung, Xiaomi...)
    val imageUrl: String = "",     // Link hình ảnh sản phẩm từ Firebase Storage
    val description: String = "",  // Mô tả sản phẩm
    val ram: String = "",          // Dung lượng RAM
    val rom: String = "",          // Bộ nhớ trong
    val stock: Int = 0             // Số lượng còn trong kho
)
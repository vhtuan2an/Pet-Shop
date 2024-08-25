package com.example.petshop.database.model
data class CartItem(
    var id_product: String = "null",
    var quantity: Int
)

data class Cart(
    val id_user: String = "null",
    val items: MutableList<CartItem> = mutableListOf()
)

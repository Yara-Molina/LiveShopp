package com.example.liveshop.features.product.domain.entities

data class Product(
    val id: String,
    val list_id: String,
    val name: String,
    val quantity: Int,
    val status: String,
    val created_at: String
)

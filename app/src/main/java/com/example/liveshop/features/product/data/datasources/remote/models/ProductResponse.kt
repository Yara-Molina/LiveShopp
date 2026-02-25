package com.example.liveshop.features.product.data.datasources.remote.models

data class ProductResponse(
    val id: String,
    val list_id: String,
    val name: String,
    val quantity: Int,
    val status: String,
    val created_at: String
)
package com.example.liveshop.features.shopping_list.domain.entities

data class ShoppingList(
    val id: String,
    val name: String,
    val description: String?,
    val createdAt: String
)
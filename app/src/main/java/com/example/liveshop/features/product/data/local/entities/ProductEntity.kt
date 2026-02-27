package com.example.liveshop.features.product.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val quantity: Int,
    val status: String,
    val listId: String
)
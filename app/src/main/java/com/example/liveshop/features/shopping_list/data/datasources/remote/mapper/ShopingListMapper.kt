package com.example.liveshop.features.shopping_list.data.datasources.remote.mapper

import com.example.liveshop.features.shopping_list.data.datasources.remote.models.ShoppingListDto
import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList

fun ShoppingListDto.toDomain() = ShoppingList(
    id = id ?: "",
    name = name,
    createdAt = created_at ?: ""
)
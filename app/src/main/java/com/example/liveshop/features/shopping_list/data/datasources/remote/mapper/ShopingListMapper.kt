package com.example.liveshop.features.shopping_list.data.datasources.remote.mapper
import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import com.example.liveshop.features.shopping_list.data.datasources.remote.models.SharedListResponse


fun SharedListResponse.toDomain(): ShoppingList {
    return ShoppingList(
        id = this.id,
        name = this.name,
        created_at = this.created_at
    )
}
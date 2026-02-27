package com.example.liveshop.features.shopping_list.data.datasources.remote.mapper
import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import com.example.liveshop.features.shopping_list.data.datasources.remote.models.SharedListResponse
import com.example.liveshop.features.shopping_list.data.local.entities.ShoppingListEntity



fun SharedListResponse.toDomain(): ShoppingList {
    return ShoppingList(id = this.id, name = this.name, created_at = this.created_at)
}


fun SharedListResponse.toEntity(): ShoppingListEntity {
    return ShoppingListEntity(id = this.id, name = this.name)
}


fun ShoppingListEntity.toDomain(): ShoppingList {
    return ShoppingList(id = this.id, name = this.name, created_at = "")
}


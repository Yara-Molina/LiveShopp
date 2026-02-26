package com.example.liveshop.features.shopping_list.domain.repositories

import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import kotlinx.coroutines.flow.Flow

interface ListRepository {
    suspend fun create_List(list: ShoppingList): ShoppingList
    suspend fun update_List(id: String, list: ShoppingList): ShoppingList
    suspend fun delete_List(id: String)
    suspend fun get_lists(): List<ShoppingList>
}
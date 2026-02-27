package com.example.liveshop.features.shopping_list.domain.repositories

import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import kotlinx.coroutines.flow.Flow

interface ListRepository {

    fun get_lists_flow(): Flow<List<ShoppingList>>
    suspend fun sync_lists()
    suspend fun create_List(name: String): ShoppingList
    suspend fun update_List(id: String, list: ShoppingList): ShoppingList
    suspend fun delete_List(id: String)
    suspend fun get_lists(): List<ShoppingList>
}
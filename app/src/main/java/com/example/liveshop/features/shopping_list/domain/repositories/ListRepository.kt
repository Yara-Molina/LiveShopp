package com.example.liveshop.features.shopping_list.domain.repositories

import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import kotlinx.coroutines.flow.Flow

interface ListRepository {
    fun getShoppingLists(): Flow<List<ShoppingList>>
    suspend fun syncLists()


    suspend fun create_List(name: String): ShoppingList
    suspend fun update_List(id: String, name: String): ShoppingList
    suspend fun delete_List(id: String)
    suspend fun get_lists(id: String): ShoppingList
}
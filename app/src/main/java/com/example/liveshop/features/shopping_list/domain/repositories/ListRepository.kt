package com.example.liveshop.features.shopping_list.domain.repositories

import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import kotlinx.coroutines.flow.Flow

interface ListRepository {
    fun getShoppingLists(): Flow<List<ShoppingList>>
    suspend fun syncLists()
}
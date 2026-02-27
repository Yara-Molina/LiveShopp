package com.example.liveshop.features.shopping_list.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.liveshop.features.shopping_list.data.local.entities.ShoppingListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM shopping_lists")
    fun getAllListsFlow(): Flow<List<ShoppingListEntity>>

    @Upsert
    suspend fun insertLists(lists: List<ShoppingListEntity>)

    @Query("DELETE FROM shopping_lists WHERE id = :id")
    suspend fun deleteListById(id: String)

    @Query("DELETE FROM shopping_lists")
    suspend fun clearAll()
}
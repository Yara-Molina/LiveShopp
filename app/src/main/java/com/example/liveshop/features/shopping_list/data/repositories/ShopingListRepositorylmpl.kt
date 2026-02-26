package com.example.liveshop.features.shopping_list.data.repositories

import com.example.liveshop.features.shopping_list.data.datasources.remote.api.SharedListApi
import com.example.liveshop.features.shopping_list.data.datasources.remote.mapper.toDomain
import com.example.liveshop.features.shopping_list.data.datasources.remote.models.SharedListCreate
import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import com.example.liveshop.features.shopping_list.domain.repositories.ListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShopingListRepositoryImpl @Inject constructor(
    private val api: SharedListApi
) : ListRepository {


    override fun getShoppingLists(): Flow<List<ShoppingList>> = flow {
        val response = api.getLists().map { it.toDomain() }
        emit(response)
    }

    override suspend fun syncLists() {
        api.getLists()
    }

    override suspend fun create_List(name: String): ShoppingList {
        return api.createList(SharedListCreate(name = name)).toDomain()
    }

    override suspend fun update_List(id: String, name: String): ShoppingList {
        return api.updateList(id, SharedListCreate(name = name)).toDomain()
    }

    override suspend fun delete_List(id: String) {
        api.deleteList(id)
    }

    override suspend fun get_lists(id: String): ShoppingList {
        val response = api.getLists().first { it.id == id }
        return response.toDomain()
    }
}
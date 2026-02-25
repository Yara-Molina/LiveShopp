package com.example.liveshop.features.shopping_list.data.repositories

import com.example.liveshop.features.shopping_list.data.datasources.remote.api.ShopingListApi
import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import com.example.liveshop.features.shopping_list.domain.repositories.ListRepository
import jakarta.inject.Inject

class ShopingListRepositorylmpl @Inject constructor(
    private val api: ShopingListApi
) : ListRepository {

    override suspend fun getAll(): List<ShoppingList> {
        return api.getLists().map { it.toDomain() }
    }

    override suspend fun create(name: String): ShoppingList {
        return api.createList(mapOf("name" to name)).toDomain()
    }

    override suspend fun update(id: String, name: String): ShoppingList {
        return api.updateList(id, mapOf("name" to name)).toDomain()
    }

    override suspend fun delete(id: String) {
        api.deleteList(id)
    }

    override suspend fun getById(id: String): ShoppingList {
        return api.getLists().first { it.id == id }.toDomain()
    }
}
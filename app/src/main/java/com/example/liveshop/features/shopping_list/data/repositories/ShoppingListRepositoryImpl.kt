package com.example.liveshop.features.shopping_list.data.repositories

import com.example.liveshop.features.product.data.datasources.remote.mapper.toDomain
import com.example.liveshop.features.shopping_list.data.datasources.remote.api.SharedListApi
import com.example.liveshop.features.shopping_list.data.datasources.remote.mapper.toDomain
import com.example.liveshop.features.shopping_list.data.datasources.remote.models.SharedListCreate
import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import com.example.liveshop.features.shopping_list.domain.repositories.ListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class ShoppingListRepositoryImpl @Inject constructor(
    private val api: SharedListApi
) : ListRepository {

    override suspend fun create_List(name: String): ShoppingList {

        val dto = SharedListCreate(name = name)
        return api.createList(dto).toDomain()
    }

    override suspend fun update_List(id: String, list: ShoppingList): ShoppingList {
        return api.updateList(id, SharedListCreate(name = list.name)).toDomain()
    }

    override suspend fun delete_List(id: String) {
        api.deleteList(id)
    }

    override suspend fun get_lists(): List<ShoppingList> {
        val lists = api.getLists()
        return lists.map { it.toDomain() }
    }
}
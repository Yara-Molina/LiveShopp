package com.example.liveshop.features.shopping_list.data.repositories

import com.example.liveshop.features.shopping_list.data.datasources.remote.api.SharedListApi
import com.example.liveshop.features.shopping_list.data.datasources.remote.mapper.toDomain
import com.example.liveshop.features.shopping_list.data.datasources.remote.mapper.toEntity

import com.example.liveshop.features.shopping_list.data.datasources.remote.models.SharedListCreate
import com.example.liveshop.features.shopping_list.data.local.dao.ShoppingListDao
import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import com.example.liveshop.features.shopping_list.domain.repositories.ListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShoppingListRepositoryImpl @Inject constructor(
    private val api: SharedListApi,
    private val dao: ShoppingListDao
) : ListRepository {
    override fun get_lists_flow(): Flow<List<ShoppingList>> {
        return dao.getAllListsFlow().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun sync_lists() {
        try {
            val remoteLists = api.getLists()
            val entities = remoteLists.map { it.toEntity() }


            dao.insertLists(entities)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun create_List(name: String): ShoppingList {

        val dto = SharedListCreate(name = name)
        val response = api.createList(dto)


        dao.insertLists(listOf(response.toEntity()))

        return response.toDomain()
    }

    override suspend fun update_List(id: String, list: ShoppingList): ShoppingList {
        val dto = SharedListCreate(name = list.name)
        val response = api.updateList(id, dto)


        dao.insertLists(listOf(response.toEntity()))

        return response.toDomain()
    }

    override suspend fun delete_List(id: String) {

        dao.deleteListById(id)

        try {
            api.deleteList(id)
        } catch (e: Exception) {

            throw e
        }
    }

    override suspend fun get_lists(): List<ShoppingList> {
        val lists = api.getLists()
        return lists.map { it.toDomain() }
    }
}
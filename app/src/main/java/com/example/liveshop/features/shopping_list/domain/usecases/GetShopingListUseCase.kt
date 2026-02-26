package com.example.liveshop.features.shopping_list.domain.usecases

import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import com.example.liveshop.features.shopping_list.domain.repositories.ListRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetShopingListUseCase @Inject constructor(
    private val repository: ListRepository
) {
    operator fun invoke(): Flow<List<ShoppingList>> {
        return repository.getShoppingLists()
    }
}
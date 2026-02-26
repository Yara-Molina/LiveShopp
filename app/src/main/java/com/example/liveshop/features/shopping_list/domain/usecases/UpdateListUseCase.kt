package com.example.liveshop.features.shopping_list.domain.usecases

import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import com.example.liveshop.features.shopping_list.domain.repositories.ListRepository
import jakarta.inject.Inject

class UpdateListUseCase @Inject constructor(
    private val repository: ListRepository
) {
    suspend operator fun invoke(id: String, list: ShoppingList): ShoppingList {
        return repository.update_List(id, list)
    }
}
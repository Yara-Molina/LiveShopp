package com.example.liveshop.features.shopping_list.domain.usecases

import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import com.example.liveshop.features.shopping_list.domain.repositories.ListRepository
import jakarta.inject.Inject

class GetListsUseCase @Inject constructor(
    private val repository: ListRepository
) {
    suspend operator fun invoke(): List<ShoppingList> {
        return repository.get_lists()
    }
}
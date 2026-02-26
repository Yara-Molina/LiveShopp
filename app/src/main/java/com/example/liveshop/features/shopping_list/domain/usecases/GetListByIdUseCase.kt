package com.example.liveshop.features.shopping_list.domain.usecases

import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import com.example.liveshop.features.shopping_list.domain.repositories.ListRepository
import jakarta.inject.Inject

class GetListByIdUseCase @Inject constructor(
    private val repository: ListRepository
) {
    suspend operator fun invoke(id: String): ShoppingList {
        return repository.get_lists(id)
    }
}
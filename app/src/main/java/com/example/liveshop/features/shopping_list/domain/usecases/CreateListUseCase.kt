package com.example.liveshop.features.shopping_list.domain.usecases

import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import com.example.liveshop.features.shopping_list.domain.repositories.ListRepository
import jakarta.inject.Inject

class CreateListUseCase @Inject constructor(
    private val repository: ListRepository
) {
    suspend operator fun invoke(list: ShoppingList): ShoppingList {
        return repository.create_List(list)
    }
}
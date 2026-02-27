package com.example.liveshop.features.shopping_list.domain.usecases

import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import com.example.liveshop.features.shopping_list.domain.repositories.ListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListsUseCase @Inject constructor(
    private val repository: ListRepository
) {

    operator fun invoke(): Flow<List<ShoppingList>> {
        return repository.get_lists_flow()
    }
}
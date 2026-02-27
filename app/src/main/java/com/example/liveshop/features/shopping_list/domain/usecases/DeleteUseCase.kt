package com.example.liveshop.features.shopping_list.domain.usecases

import com.example.liveshop.features.shopping_list.domain.repositories.ListRepository
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val repository: ListRepository
) {
    suspend operator fun invoke(id: String) {
        repository.delete_List(id)
    }
}